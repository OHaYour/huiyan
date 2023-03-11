package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    /**
     * 查询出所有的用户
     * @return
     */
    List<User> queryUsersInfo();

    /**
     * 查询出用户被警告的次数
     * @param userId
     * @return
     */
    User queryUserLevel(String userId);

    /**
     * 更新用户被警告的次数
     * @param user
     */
    void updateUserLevel(User user);
}
