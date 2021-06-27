package com.tc.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Department {
    /**
     * 部门 id
     */
    private int id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 子部门 id 列表
     */
    private List<Integer> subDepartIds = new ArrayList<>();
}
