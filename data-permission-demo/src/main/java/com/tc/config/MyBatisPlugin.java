package com.tc.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.tc.client.UserServiceClient;
import com.tc.common.util.PluginUtils;
import com.tc.domain.DataScopeValue;
import com.tc.domain.Department;
import com.tc.domain.User;
import com.tc.service.DataScopConfigService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;


@Intercepts({@Signature(type = org.apache.ibatis.executor.Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Component
@Slf4j
public class MyBatisPlugin implements Interceptor {

    @Autowired
    private DataScopConfigService dataScopConfigService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        String mapperMethodName = getMapperMethodName(invocation);

        if (!checkDataScopeAnnotation(mapperMethodName)) return invocation.proceed();

        DataScopeValue dataScopeValue = getDataScopeValueByMapperMethodName(mapperMethodName);

        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        if (target instanceof Executor) {
            final Executor executor = (Executor) target;
            Object parameter = args[1];
            boolean isUpdate = args.length == 2;
            MappedStatement ms = (MappedStatement) args[0];
            if (!isUpdate && ms.getSqlCommandType() == SqlCommandType.SELECT) {
                RowBounds rowBounds = (RowBounds) args[2];
                ResultHandler resultHandler = (ResultHandler) args[3];
                BoundSql boundSql;
                if (args.length == 4) {
                    boundSql = ms.getBoundSql(parameter);
                } else {
                    boundSql = (BoundSql) args[5];
                }
                PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
                User user = UserServiceClient.getUser();
                mpBs.sql(joinPermissionSqlFragment(mpBs.sql(), dataScopeValue, user));
                CacheKey cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
                return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            }
        }
        return invocation.proceed();
    }

    private DataScopeValue getDataScopeValueByMapperMethodName(String mapperMethodName) {
        Map<String, DataScopeValue> dataScopeValueMap = dataScopConfigService.dataScopeValueMap;
        for (String key : dataScopeValueMap.keySet()) {
            if (key.equals(mapperMethodName)) {
                return dataScopeValueMap.get(key);
            }
        }
        return null;
    }

    private boolean checkDataScopeAnnotation(String mapperMethodName) {
        Map<String, DataScopeValue> dataScopeValueMap = dataScopConfigService.dataScopeValueMap;
        List<String> methodNames = new ArrayList<>();
        dataScopeValueMap.forEach((mehtodName, dataScoepValue) -> {
            methodNames.add(mehtodName);
        });
        if (methodNames.contains(mapperMethodName)) {
            return true;
        }
        return false;
    }

    private String getMapperMethodName(Invocation invocation) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String id = mappedStatement.getId();
        List<String> methodStrList = splitConvertToList(id, "\\.");
        return methodStrList.get(methodStrList.size() - 2) + "." + methodStrList.get(methodStrList.size() - 1);
    }

    private String joinPermissionSqlFragment(String sql, DataScopeValue dataScopeValue, User user) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Select) {
                this.processSelect((Select) statement, dataScopeValue, user);
            }
            return statement.toString();
        } catch (JSQLParserException e) {
            throw ExceptionUtils.mpe("Failed to process, Error SQL: %s", e.getCause(), sql);
        }
    }


    protected void processSelect(Select select, DataScopeValue dataScopeValue, User user) {
        processSelectBody(select.getSelectBody(), dataScopeValue, user);
        List<WithItem> withItemsList = select.getWithItemsList();
        if (!CollectionUtils.isEmpty(withItemsList)) {
            withItemsList.forEach(item -> processSelectBody(item, dataScopeValue, user));
        }
    }


    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    @Override
    public void setProperties(Properties arg0) {

    }


    public List<String> splitConvertToList(String str, String split) {
        if (!StringUtils.hasLength(str)) {
            return new ArrayList<String>();
        }
        String[] splitArr = str.split(split);
        ArrayList<String> list = new ArrayList<String>(splitArr.length);
        for (String string : splitArr) {
            list.add(string);
        }
        return list;
    }


    protected void processSelectBody(SelectBody selectBody, DataScopeValue dataScopeValue, User user) {
        if (selectBody == null) {
            return;
        }
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody, dataScopeValue, user);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            processSelectBody(withItem.getSelectBody(), dataScopeValue, user);
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodys = operationList.getSelects();
            if (CollectionUtils.isNotEmpty(selectBodys)) {
                selectBodys.forEach(body -> processSelectBody(body, dataScopeValue, user));
            }
        }
    }


    protected void processPlainSelect(PlainSelect plainSelect, DataScopeValue dataScopeValue, User user) {
        FromItem fromItem = plainSelect.getFromItem();
        Expression where = plainSelect.getWhere();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            plainSelect.setWhere(builderExpression(where, fromTable, dataScopeValue, user));
        }
    }

    protected Expression builderExpression(Expression currentExpression, Table table, DataScopeValue dataScopeValue, User user) {
        switch (dataScopeValue.getDataScopeTypeEnum()) {
            case BASED_ON_Organization:
                return buildOrganizationExpression(currentExpression, table, dataScopeValue, user);
            case BASED_ON_SHOP:
                return buildShopExpression(currentExpression, table, dataScopeValue, user);
        }
        return null;
    }

    private Expression buildShopExpression(Expression currentExpression, Table table, DataScopeValue dataScopeValue, User user) {

        EqualsTo equalsTo = new EqualsTo();
        Column column = new Column();
        column.setColumnName((StringUtils.hasLength(dataScopeValue.getAlisa()) ? (dataScopeValue.getAlisa() + ".") : "") + dataScopeValue.getColumn());
        equalsTo.setLeftExpression(column);
        equalsTo.setRightExpression(new StringValue(user.getShopId() + ""));
        if (currentExpression == null) {
            return equalsTo;
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), equalsTo);
        } else {
            return new AndExpression(currentExpression, equalsTo);
        }
    }

    private Expression buildOrganizationExpression(Expression currentExpression, Table table, DataScopeValue dataScopeValue, User user) {
        Expression joinedExpression = null;
        if (user.getDataScope() == 0) { // 查询本部门数据
            InExpression inExpression = new InExpression();
            Column column = new Column();
            column.setColumnName((StringUtils.hasLength(dataScopeValue.getAlisa()) ? (dataScopeValue.getAlisa() + ".") : "") + dataScopeValue.getColumn());
            inExpression.setLeftExpression(column);
            Statement statement = null;
            try {
                statement = CCJSqlParserUtil.parse("select id from t_employee where department_id = " + user.getDepartment().getId());
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
            Select select = (Select) statement;
            SelectBody departSql = select.getSelectBody();

            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(departSql);
            inExpression.setRightExpression(subSelect);
            joinedExpression = inExpression;
        } else if (user.getDataScope() == 1) { // 查询本人数据
            EqualsTo equalsTo = new EqualsTo();
            Column column = new Column();
            column.setColumnName((StringUtils.hasLength(dataScopeValue.getAlisa()) ? (dataScopeValue.getAlisa() + ".") : "") + dataScopeValue.getColumn());
            equalsTo.setLeftExpression(column);
            equalsTo.setRightExpression(new LongValue(user.getUserId()));
            joinedExpression = equalsTo;
        }
        if (user.getDataScope() == 2) { // 查询本部门及子部门数据
            InExpression inExpression = new InExpression();
            Column column = new Column();
            column.setColumnName((StringUtils.hasLength(dataScopeValue.getAlisa()) ? (dataScopeValue.getAlisa() + ".") : "") + dataScopeValue.getColumn());
            inExpression.setLeftExpression(column);
            Statement statement = null;
            try {
                statement = CCJSqlParserUtil.parse("select id from t_employee where department_id in ( " + joinStrList(user.getDepartment()) + ")");
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
            Select select = (Select) statement;
            SelectBody departSql = select.getSelectBody();

            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(departSql);
            inExpression.setRightExpression(subSelect);
            joinedExpression = inExpression;
        }

        if (currentExpression == null) {
            return joinedExpression;
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), joinedExpression);
        } else {
            return new AndExpression(currentExpression, joinedExpression);
        }
    }

    private String joinStrList(Department department) {
        StringBuffer result = new StringBuffer();
        Integer departmentId = department.getId();
        result.append(departmentId);
        if (CollectionUtils.isNotEmpty(department.getSubDepartIds())) {
            for (Integer str : department.getSubDepartIds()) {
                result.append("," + str);
            }
        }
        return result.toString();
    }
}