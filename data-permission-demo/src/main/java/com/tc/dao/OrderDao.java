package com.tc.dao;

import com.tc.common.DataScopeTypeEnum;
import com.tc.common.annotation.DataScope;
import com.tc.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderDao {

    /**
     * 查询订单列表
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return
     */
    @DataScope(type = DataScopeTypeEnum.BASED_ON_SHOP, alisa = "o", column = "shop_id")
    List<Order> getOrderList(Integer page, Integer size);
}
