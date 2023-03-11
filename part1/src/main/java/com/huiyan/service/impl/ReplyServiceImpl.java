package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huiyan.dao.ReplyDao;
import com.huiyan.pojo.Reply;
import com.huiyan.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao replyDao;
    @Override
    public List<Reply> queryReplysByVideoId(Reply reply) {
//        LambdaQueryWrapper<Reply> wrapper=new LambdaQueryWrapper<>();
//        wrapper.eq(Reply::getFromVid,reply.getFromVid());
//        List<Reply>replys=replyDao.selectList(wrapper);
        return null;
    }

    @Override
    public List<Reply> queryTopicReply(String fromVid) {

        List<Reply> replys=replyDao.queryTopicReply(fromVid);
        return replys;
    }

    @Override
    public void addTopicReply(Reply reply) {
        //获取当前时间的毫秒数,插入replyId
        Date date = new Date();
        long dateTime=date.getTime();
        //将时间的毫秒数转为string类型
        String randomNum=String.valueOf(dateTime);
        String replyId=randomNum.substring(randomNum.length()-8,randomNum.length());

        reply.setTopicTime(date);
        reply.setReplyId(replyId);
        reply.setReplyType("0");

        replyDao.addTopicReply(reply);
    }
}
