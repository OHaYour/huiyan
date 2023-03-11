package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huiyan.dao.KeyWordDao;
import com.huiyan.pojo.KeyWord;
import com.huiyan.service.KeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KeyWordServiceImpl implements KeyWordService {

    @Autowired
    private KeyWordDao keyWordDao;

    @Override
    public List<KeyWord> queryAllKeyWords() {

        LambdaQueryWrapper<KeyWord> wrapper=new LambdaQueryWrapper<>();
        List<KeyWord> keyWords=keyWordDao.selectList(wrapper);
        return keyWords;
    }

    @Override
    public void queryKeyWordPage() {
        //参数一：当前页
        //参数二：页面大小
        Page<KeyWord> page = new Page(0,5);
        keyWordDao.selectPage(page,null);

        List<KeyWord> keyWords = page.getRecords();
        for (KeyWord user : keyWords) {
            System.out.println(user);
        }
        System.out.println("总数="+page.getTotal());
        System.out.println("页面大小="+page.getSize());
        System.out.println("页面总数="+page.getPages());
    }

    @Override
    public void addKeyWord(KeyWord keyWord) {

        //获取当前时间的毫秒数,插入userId
        Date date = new Date();
        long dateTime=date.getTime();
        System.out.println("当前时间的毫秒数："+dateTime);
        //将时间的毫秒数转为string类型
        String randomNum=String.valueOf(dateTime);
        String userId=randomNum.substring(randomNum.length()-8,randomNum.length());
        keyWord.setKeyWordId(userId);

        keyWordDao.insert(keyWord);
    }

    @Override
    public void delKeyWord(String keywordId) {
        keyWordDao.deleteById(keywordId);
    }
}
