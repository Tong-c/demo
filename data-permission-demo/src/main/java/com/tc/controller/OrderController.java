package com.tc.controller;

import com.tc.domain.Order;
import com.tc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return
     */
    @GetMapping("/list")
    private List<Order> getOrderList(Integer page, Integer size) {
        return orderService.getOrderList(page, size);
    }

}
