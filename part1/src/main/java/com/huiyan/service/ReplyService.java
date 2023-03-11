package com.huiyan.service;

import com.huiyan.pojo.Reply;

import java.util.List;

public interface ReplyService {

    /**
     * 通过videoId,TopicId查询出对应的所有回复
     * @param reply
     * @return
     */
    List<Reply> queryReplysByVideoId(Reply reply);

    /**
     * 查出视频下所有的回复
     * @param fromVid
     * @return
     */
    List<Reply> queryTopicReply(String fromVid);

    /**
     * 添加评论的回复
     * @param reply
     */
    void addTopicReply(Reply reply);
}
