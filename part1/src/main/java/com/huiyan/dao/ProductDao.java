package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends BaseMapper<Product> {
}
