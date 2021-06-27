package com.tc.domain;

import com.tc.common.DataScopeTypeEnum;
import lombok.Data;

@Data
public class DataScopeValue {

    /**
     * 数据权限类型枚举
     */
    private DataScopeTypeEnum dataScopeTypeEnum;

    /**
     * 别名
     */
    private String alisa;

    /**
     * 列名
     */
    private String column;
}
