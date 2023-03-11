package com.huiyan.service;


import com.huiyan.pojo.Orders;

public interface AlipayService {

    void  aliPay(Orders orders) throws Exception;


}