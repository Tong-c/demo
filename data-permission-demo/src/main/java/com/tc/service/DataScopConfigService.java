package com.tc.service;

import com.tc.common.annotation.DataScope;
import com.tc.domain.DataScopeValue;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class DataScopConfigService {

    public static Map<String, DataScopeValue> dataScopeValueMap = new HashMap<>();

    @PostConstruct
    private void initDataScopeMethodMap() {
        this.refreshDataScopeMethodMap();
    }

    /**
     * 刷新 所有添加数据范围注解的接口方法配置
     *
     * @return
     */
    private void refreshDataScopeMethodMap() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.tc.dao")).setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(DataScope.class);
        for (Method method : methods) {
            DataScope dataScopeAnnotation = method.getAnnotation(DataScope.class);
            if (dataScopeAnnotation != null) {
                DataScopeValue dataScopeValue = new DataScopeValue();
                dataScopeValue.setDataScopeTypeEnum(dataScopeAnnotation.type());
                dataScopeValue.setAlisa(dataScopeAnnotation.alisa());
                dataScopeValue.setColumn(dataScopeAnnotation.column());
                dataScopeValueMap.put(method.getDeclaringClass().getSimpleName() + "." + method.getName(), dataScopeValue);
            }
        }

    }

}
