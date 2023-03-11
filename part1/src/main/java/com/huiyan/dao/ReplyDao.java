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
}
