package com.tc.domain;

import lombok.Data;

@Data
public class Coupon {

    /**
     * 数据表主键 id
     */
    private int id;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券创建人
     */
    private String nickName;

}
