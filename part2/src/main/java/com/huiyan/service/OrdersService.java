package com.huiyan.service;

import com.huiyan.pojo.Orders;

import java.util.List;

public interface OrdersService {

    /**
     * 查询30日内的订单数量
     * @return
     */
    String queryOrdersAmount();

    /**
     * 查询30日内收入
     * @return
     */
    String queryOrdersBalance();
}
