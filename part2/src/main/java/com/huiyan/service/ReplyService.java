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

    /**
     * 查询进30天的回复数量
     * @return
     */
    String queryReplyNum();

    /**
     * 查询状态为0
     * 不通过的回复
     * @return
     */
    List<Reply> queryReplysByType();

    /**
     * 通过replyId修改状态
     * @param replyId
     */
    void changType0(String replyId);

    /**
     * 通过replyId修改状态
     * @param replyId
     */
    void changType2(String replyId);
}
