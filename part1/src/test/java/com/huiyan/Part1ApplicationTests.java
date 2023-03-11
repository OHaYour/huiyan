package com.huiyan;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huiyan.dao.UserDao;
import com.huiyan.dao.VideoDao;
import com.huiyan.pojo.KeyWord;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.KeyWordService;
import com.huiyan.service.VCodeService;
import com.huiyan.util.BaiduMapUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class Part1ApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VideoDao videoDao;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private VCodeService vCodeService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private KeyWordService keyWordService;

    @Test
    void contextLoads() {

//        String str= BaiduMapUtil.baidumap();
//        System.out.println(str);


//        redisTemplate.opsForValue().set("5565","vip",3, TimeUnit.DAYS);
//
//        //-2表示不存在
//        Long times=redisTemplate.getExpire("5565", TimeUnit.DAYS);
//        Long times1=redisTemplate.getExpire("5565", TimeUnit.SECONDS);
//        System.out.println(times);
//        System.out.println(times1);

//        String value=redisTemplate.opsForValue().get("1134");
//        System.out.println(value);

//        List<KeyWord> keyWords=keyWordService.queryAllKeyWords();
//        System.out.println(keyWords);

        keyWordService.queryKeyWordPage();


    }

}
