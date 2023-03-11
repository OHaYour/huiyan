package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.huiyan.dao.OrdersDao;
import com.huiyan.pojo.Orders;
import com.huiyan.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;


    @Override
    public String queryOrdersAmount() {
        String ordersAmount=ordersDao.queryOrdersAmount();
        return ordersAmount;
    }

    @Override
    public String queryOrdersBalance() {
        String ordersBalance=ordersDao.queryOrdersBalance();
        return ordersBalance;
    }
}
