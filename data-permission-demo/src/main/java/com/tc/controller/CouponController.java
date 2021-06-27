package com.tc.controller;

import com.tc.domain.Coupon;
import com.tc.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 查询优惠券列表
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return
     */
    @GetMapping("/list")
    public List<Coupon> getCouponList(Integer page, Integer size) {
        return couponService.getCouponList(page, size);
    }


}
