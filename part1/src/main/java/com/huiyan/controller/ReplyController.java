package com.huiyan.controller;

import com.huiyan.pojo.Reply;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.ReplyService;
import com.huiyan.service.UserService;
import com.huiyan.util.BaiduMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @RequestMapping("/addTopicReply")
    @ResponseBody
    public String addTopicReply(String topicId, String content, String toNickName,String toUid, HttpSession session){

        User loginUser= (User) session.getAttribute("loginUser");
        Video videoPlay=(Video) session.getAttribute("videoPlay");

        Reply reply=new Reply();
        reply.setTopicId(topicId);
        reply.setContent(content);
        reply.setToUserNickName(toNickName);
        reply.setToUid(toUid);
        reply.setFromUserNickName(loginUser.getUserNickName());
        reply.setFromUid(loginUser.getUserId());
        reply.setFromVid(videoPlay.getVideoId());

        User user=userService.queryUserByUserId(loginUser.getUserId());
        String userLevel1=user.getUserLevel();
        int userLevel2=Integer.parseInt(userLevel1);
        if (userLevel2>=10){
            reply.setReplyFlag("网暴者");
        }
        String ipAdress= BaiduMapUtil.baidumap();
        reply.setIpAdress(ipAdress);

        replyService.addTopicReply(reply);

        return "ok";
    }
}
