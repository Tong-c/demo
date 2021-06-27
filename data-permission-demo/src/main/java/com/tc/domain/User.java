package com.tc.domain;

import lombok.Data;

@Data
public class User {

    /**
     * 用户 Id
     */
    private int userId;

    /**
     * 数据权限范围
     */
    private int dataScope;

    /**
     * 用户所属门店 id
     */
    private int shopId;

    /**
     * 部门
     */
    private Department department;
}
