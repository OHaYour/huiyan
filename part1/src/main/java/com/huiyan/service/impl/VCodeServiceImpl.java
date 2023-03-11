package com.huiyan.service.impl;

import com.huiyan.service.VCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VCodeServiceImpl implements VCodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 将验证码存储到Redis中
     * @param key
     * @param value
     */
    @Override
    public void addInfoToRedis(String key, String value) {

        //往redis中存储信息，并且设置有效期，第三个参数：有效期 第四个参数：有效期数值的单位
        redisTemplate.opsForValue().set(key,value,300, TimeUnit.SECONDS);

    }

    /**
     * 根据key值从redis中取出对应的value值
     * @param key
     * @return
     */
    @Override
    public String getValueByKey(String key) {
        String value=redisTemplate.opsForValue().get(key);
        return value;
    }
}
