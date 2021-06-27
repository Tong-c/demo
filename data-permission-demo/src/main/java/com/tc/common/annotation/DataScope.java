package com.tc.common.annotation;

import com.tc.common.DataScopeTypeEnum;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 表别名
     * 如果 @DataScope 注解所标识的语句涉及到多表连接，需要指定数据权限所作用表的别名，且该别名与 sql 中的别名一致
     *
     * @return
     */
    String alisa() default "";

    /**
     * 列名
     *
     * @return
     */
    String column() default "";

    /**
     * 数据权限类型
     *
     * @return
     */
    DataScopeTypeEnum type();

}
