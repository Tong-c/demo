package com.tc.dao;

import com.tc.common.DataScopeTypeEnum;
import com.tc.common.annotation.DataScope;
import com.tc.domain.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CouponDao {

    /**
     * 查询优惠券列表
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return 优惠券列表
     */
    @DataScope(type = DataScopeTypeEnum.BASED_ON_Organization, alisa = "t", column = "create_user_id")
    List<Coupon> selectCouponList(Integer page, Integer size);
}
