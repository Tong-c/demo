package com.tc.service;

import com.tc.dao.OrderDao;
import com.tc.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 查询订单列表
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return
     */
    public List<Order> getOrderList(Integer page, Integer size) {
        return orderDao.getOrderList(page, size);
    }
}
