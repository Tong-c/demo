package com.tc.service;

import com.tc.dao.CouponDao;
import com.tc.domain.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponDao couponDao;

    /**
     * 查询优惠券列表
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return 优惠券列表
     */
    public List<Coupon> getCouponList(Integer page, Integer size) {
        return couponDao.selectCouponList(page, size);
    }
}
