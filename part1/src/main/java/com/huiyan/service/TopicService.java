package com.huiyan.service;

import com.huiyan.pojo.Topic;

import java.util.List;

public interface TopicService {

    /**
     * 通过videoId查询出评论信息
     * @param topic
     * @return
     */
    List<Topic> queryTopicByVideoId(Topic topic);

    /**
     * 通过videoId查询视频下的所有评论
     * @param videoId
     * @return
     */
    List<Topic>queryVideoTopic(String fromVid);

    /**
     * 添加评论
     * @param topic
     */
    void addTopic(Topic topic);
}
