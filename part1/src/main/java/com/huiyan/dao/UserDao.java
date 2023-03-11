package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    List<Video> queryUserByVideoId();


}
