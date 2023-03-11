package com.huiyan.service;

import com.huiyan.pojo.Orders;

import java.util.List;

public interface OrdersService {

    /**
     * 新增订单
     * @param orders
     */
    void addOrder(Orders orders);

    /**
     * 修改订单状态
     * @param orders
     */
    void updataStatus(Orders orders);

    /**
     * 通过userId查询出他的所有订单
     * @param userId
     * @return
     */
    List<Orders> queryOrdersByUserId(String userId);
}
