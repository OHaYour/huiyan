package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//@Mapper
@Repository
public interface OrdersDao extends BaseMapper<Orders> {
}
