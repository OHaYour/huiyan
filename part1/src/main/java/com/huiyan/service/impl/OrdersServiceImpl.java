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
    public void addOrder(Orders orders) {

        ordersDao.insert(orders);
    }

    /**
     * 回调成功后将订单状态改为-->“1”已支付
     * @param orders
     */
    @Override
    public void updataStatus(Orders orders) {
        LambdaUpdateWrapper<Orders> lambdaUpdateWrapper=new LambdaUpdateWrapper<Orders>();
        lambdaUpdateWrapper.eq(Orders::getOrderNo,orders.getOrderNo()).set(Orders::getStatus,"1");
        ordersDao.update(null,lambdaUpdateWrapper);
    }

    @Override
    public List<Orders> queryOrdersByUserId(String userId) {
        LambdaQueryWrapper<Orders> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId,userId).eq(Orders::getStatus,"1");
        List<Orders> orders=ordersDao.selectList(wrapper);
        return orders;
    }
}
