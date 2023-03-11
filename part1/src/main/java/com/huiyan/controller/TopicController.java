package com.huiyan.controller;

import com.huiyan.pojo.KeyWord;
import com.huiyan.pojo.Topic;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.KeyWordService;
import com.huiyan.service.TopicService;
import com.huiyan.service.UserService;
import com.huiyan.util.BaiduMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private KeyWordService keyWordService;


    @RequestMapping("/addTopic")
    @ResponseBody
    public String addTopic(String content, HttpSession session){

        boolean flag=true;
        //查询出所有的屏蔽关键字
        List<KeyWord> keyWords=keyWordService.queryAllKeyWords();
        for (KeyWord key:keyWords) {
           if (content.contains(key.getKeyWordContent())){
               flag=false;
               return "keywordError";
           }
        }
        if (flag){
            User loginUser= (User) session.getAttribute("loginUser");
            User videoUser= (User) session.getAttribute("videoUser");
            Video videoPlay=(Video) session.getAttribute("videoPlay");

            Topic topic=new Topic();
            topic.setContent(content);
            topic.setFromUid(loginUser.getUserId());
            topic.setFromVid(videoPlay.getVideoId());
            //查询用户被警告的次数
            User user=userService.queryUserByUserId(loginUser.getUserId());
            String userLevel1=user.getUserLevel();
            int userLevel2=Integer.parseInt(userLevel1);
            if (userLevel2>=10){
                topic.setTopicFlag("网暴者");
            }
            //设置ip地址
            String ipAdress= BaiduMapUtil.baidumap();
            topic.setIpAdress(ipAdress);

            topicService.addTopic(topic);
            return "ok";
        }else {
            return "error";
        }


    }

    @RequestMapping("/toUserShiMing")
    public String toUserShiMing(){
        return "userShiMing";
    }
}
