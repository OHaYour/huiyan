package com.huiyan.service;

public interface VCodeService {

    /**
     * 添加信息到数据库中，并且设置有效期
     * @param key
     * @param value
     */
    void addInfoToRedis(String key,String value);


    //根据key取出对应的value值
    String getValueByKey(String key);
}
