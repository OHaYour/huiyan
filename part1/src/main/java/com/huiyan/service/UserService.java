package com.huiyan.service;

import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;

import java.util.List;

public interface UserService {

    /**
     * 查询user表中的所有数据
     * @param user
     * @return
     */
    List<User> queryUser(User user);

    /**
     * 通过传入user的单个或者全部信息,查询单个user信息
     * @param user
     * @return
     */
    User queryUserInfo(User user);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    void addUser(User user);

    /**
     * 通过用户的邮箱更新用户信息
     * @param user
     * @param email
     */
    void updateUser(User user,String email);

    /**
     * 登录完成之后通过userId修改密码
     * @param user
     * @return
     */
    void updateUserByUserId(User user);


    /**
     * 查询用户真实信息是否被注册过
     * @param userRealName
     * @param userRealId
     * @return
     */
    User selectUserRealInfo(String userRealName,String userRealId);

    /**
     * 通过用户的userId查询用户全部信息
     * @param userId
     * @return
     */
    User queryUserByUserId(String userId);


    List<Video> queryUserByVideoId();
}
