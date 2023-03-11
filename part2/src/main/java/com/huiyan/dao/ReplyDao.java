package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.Reply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyDao extends BaseMapper<Reply> {

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
