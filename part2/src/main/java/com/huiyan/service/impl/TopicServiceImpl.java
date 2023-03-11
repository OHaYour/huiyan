package com.huiyan.service.impl;

import com.huiyan.dao.TopicDao;
import com.huiyan.pojo.Topic;
import com.huiyan.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Override
    public List<Topic> queryTopicByVideoId(Topic topic) {

//        LambdaQueryWrapper<Topic> wrapper=new LambdaQueryWrapper<>();
//        wrapper.eq(Topic::getFromVid,topic.getFromVid());
//        List<Topic> topics=topicDao.selectList(wrapper);
        return null;
    }

    @Override
    public List<Topic> queryVideoTopic(String fromVid) {
        List<Topic> topics=topicDao.queryVideoTopic(fromVid);
        return topics;
    }

    @Override
    public void addTopic(Topic topic) {
        //获取当前时间的毫秒数,插入topicId
        Date date = new Date();
        long dateTime=date.getTime();
        //将时间的毫秒数转为string类型
        String randomNum=String.valueOf(dateTime);
        String topicId=randomNum.substring(randomNum.length()-8,randomNum.length());

        topic.setTopicId(topicId);
        topic.setTopicType("1");
        topic.setTopicTime(date);

        topicDao.addTopic(topic);



    }

    @Override
    public String queryTopicNum() {
        String topicNum=topicDao.queryTopicNum();
        return topicNum;
    }
}
