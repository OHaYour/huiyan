package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huiyan.dao.KeyWordDao;
import com.huiyan.pojo.KeyWord;
import com.huiyan.service.KeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
