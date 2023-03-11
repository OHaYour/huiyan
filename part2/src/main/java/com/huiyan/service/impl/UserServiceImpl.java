package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huiyan.dao.UserDao;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;


    @Override
    public List<User> queryUser(User user) {
        return null;
    }

    @Override
    public User queryUserInfo(User user) {

        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserEmail,user.getUserEmail());
        User user1=userDao.selectOne(queryWrapper);
        return user1;


    }

    @Override
    public void addUser(User user) {

        //获取当前时间的毫秒数,插入userId
        Date date = new Date();
        long dateTime=date.getTime();
        System.out.println("当前时间的毫秒数："+dateTime);
        //将时间的毫秒数转为string类型
        String randomNum=String.valueOf(dateTime);
        String userId=randomNum.substring(randomNum.length()-8,randomNum.length());
        user.setUserId(userId);
        //写入数据库
        userDao.insert(user);

    }

    @Override
    public void updateUser(User user, String email) {
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("userEmail",email);
        userDao.update(user,updateWrapper);
    }

    @Override
    public void updateUserByUserId(User user) {

        String userId=user.getUserId();

        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("userId",userId);
        userDao.update(user,updateWrapper);

    }

    @Override
    public User selectUserRealInfo(String userRealName, String userRealId) {

        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserRealName,userRealName).or().eq(User::getUserRealId,userRealId);
        User user=userDao.selectOne(wrapper);
        return user;
    }

    @Override
    public User queryUserByUserId(String userId) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId,userId);
        User user=userDao.selectOne(queryWrapper);
        return user;
    }

    @Override
    public List<User> queryUsersInfo() {

        List<User> users=userDao.queryUsersInfo();
        return users;
    }

    @Override
    public User queryUserLevel(String userId) {
        User user=userDao.queryUserLevel(userId);

        return user;
    }

    @Override
    public void updateUserLevel(User user) {
        userDao.updateUserLevel(user);
    }


}
