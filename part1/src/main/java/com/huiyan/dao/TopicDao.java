package com.huiyan.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDao extends BaseMapper<Topic> {

    /**
     * 通过videoId查询视频下的所有评论
     * @param fromVid
     * @return
     */
    List<Topic>queryVideoTopic(String fromVid);

    /**
     * 添加评论
     * @param topic
     */
    void addTopic(Topic topic);
}
