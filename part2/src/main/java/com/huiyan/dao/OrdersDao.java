package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.Orders;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository
public interface OrdersDao extends BaseMapper<Orders> {

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
