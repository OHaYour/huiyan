package com.huiyan.service.impl;

import com.alipay.api.AlipayApiException;

import com.huiyan.dao.OrdersDao;
import com.huiyan.pojo.Orders;
import com.huiyan.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author king
 * @description
 * @create 2019-08-08 18:49
 */

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private OrdersDao ordersDao;

    /**
     * @throws IOException
     * @throws AlipayApiException
     */
    @Override
    public void aliPay(Orders orders) throws Exception {

        ordersDao.insert(orders);

    }


}
