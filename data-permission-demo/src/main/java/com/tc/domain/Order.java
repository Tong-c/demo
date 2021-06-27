package com.tc.domain;

import lombok.Data;

@Data
public class Order {

    /**
     * 数据表主键 id
     */
    private int id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 门店 id
     */
    private int shopId;

    /**
     * 用户 id
     */
    private int userId;

    /**
     * 实付金额
     */
    private int actualAmount;

    /**
     * 订单金额
     */
    private int orderAmount;
}
