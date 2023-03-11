package com.huiyan.controller;

import com.huiyan.pojo.KeyWord;
import com.huiyan.pojo.Reply;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @Autowired
    private KeyWordService keyWordService;

    /**
     * 控制去首页
     * @return
     */
    @RequestMapping("/")
    public String toIndex(){
        return "login";

    }

    @RequestMapping("/toIndex")
    public String toIndexx(HttpSession session){


        String ordersNum=ordersService.queryOrdersAmount();
        String ordersBalance=ordersService.queryOrdersBalance();
        session.setAttribute("ordersNum",ordersNum);
        session.setAttribute("ordersBalance",ordersBalance);

        String videoNum=videoService.queryVideoAmount();
        session.setAttribute("videoNum",videoNum);

        String topicNum=topicService.queryTopicNum();
        String replyNum=replyService.queryReplyNum();
        int topicNum1=Integer.parseInt(topicNum);
        int replyNum1=Integer.parseInt(replyNum);
        int commentNum=topicNum1+replyNum1;
        String commentNum1=String.valueOf(commentNum);
        session.setAttribute("commentNum",commentNum1);

        return "index";
    }


    @RequestMapping("/toUsers")
    public String toUsers(HttpSession session){
        List<User> users=userService.queryUsersInfo();
        session.setAttribute("users",users);

        return "users";
    }

    @RequestMapping("/toVideo")
    public String toVideo(HttpSession session){
        List<Video> videos=videoService.queryAllVideos();
        session.setAttribute("videos",videos);

        return "video";
    }

    @RequestMapping("/toUpload")
    public String toUpload(){
        return "uploadVideo";
    }

    @RequestMapping("toComment")
    public String toComment(){
        return "comment";
    }

    @RequestMapping("/toReply")
    public String toReply(HttpSession session){

        List<Reply> replys=replyService.queryReplysByType();
        session.setAttribute("replys",replys);
        return "reply";
    }

    @RequestMapping("/toVideoPlay")
    public String toVideoPlay(String videoId,HttpSession session){

        Video video=videoService.queryVideoByVideoId(videoId);
        session.setAttribute("videoPlay",video);

        return "videoPlay";
    }

    @RequestMapping("/changeVideoState")
    @ResponseBody
    public String changeVideoState(String videoId){

        videoService.updateVideoState(videoId);
        return "ok";
    }

    @RequestMapping("/addVideoAdvice")
    @ResponseBody
    public String addVideoAdvice(String videoId,String videoAdvice){
        Video video=new Video();
        video.setVideoId(videoId);
        video.setVideoNote(videoAdvice);
        videoService.addVideoAdvidce(video);
        return "ok";
    }

    @RequestMapping("/checkVideo")
    @ResponseBody
    public String checkVideo(String videoTitle, String videoInfo, @RequestParam("videoUrl") MultipartFile videoUrl, @RequestParam("imgUrl") MultipartFile imgUrl, String videoTime, HttpSession session){


        Video video=new Video();
        video.setVideoTitle(videoTitle);
        video.setVideoInfo(videoInfo);
        video.setVideoTime(videoTime);
        video.setVideoType("电影");
        video.setVideoPlayAmount("0");
        video.setVideoLike("0");
        video.setVideoCoin("0");
        video.setVideoCollection("0");
        video.setUserId("admin");
        //管理员上传直接通过
        video.setVideoState("1");

        //获取到视频和图片的名称
        String videoName = videoUrl.getOriginalFilename();
        String imgName=imgUrl.getOriginalFilename();
        //截取出文件格式
        String videoFormat=videoName.substring(videoName.lastIndexOf(".")+1);
        String imgFormat=imgName.substring(imgName.lastIndexOf(".")+1);


        if (!(videoFormat.equals("mp4")||videoFormat.equals("avi")||videoFormat.equals("mov"))){
            //判断上传视频格式
            return "VideoFormatError";
        } else if(!(imgFormat.equals("jpg")||imgFormat.equals("png"))){
            //判断上传图片格式
            return "ImgFormatError";
        }else {

            //加个时间戳，尽量避免文件名称重复
            videoName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+videoName;
            imgName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+imgName;

//            System.out.println("（加时间戳）保存的文件名为: "+videoName);
//            System.out.println("（加时间戳）保存的文件名为: "+imgName);

            //文件绝对路径
            String videoPath = "D:/JavaStudy/part1/src/main/resources/static/video/" +videoName;
            String imgPath = "D:/JavaStudy/part1/src/main/resources/static/img/" +imgName;

            //文件存储数据库相对路径
            String videoPath1 = "/video/" +videoName;
            String imgPath1 = "/img/" +imgName;
            video.setVideoPath(videoPath1);
            video.setImgPath(imgPath1);
//            System.out.println("保存文件绝对路径"+videoPath+"\n");

            //创建文件路径
            File video1=new File(videoPath);
            File img1=new File(imgPath);

            //判断是否存在同名的视频和图片
            if (video1.exists()){
                return "videoExist";
            }
            if (img1.exists()){
                return "imgExist";
            }

            //判断文件父目录是否存在
            if (!video1.getParentFile().exists()) {
                video1.getParentFile().mkdir();
            }
            if (!img1.getParentFile().exists()) {
                img1.getParentFile().mkdir();
            }

            try {

                String url1="http://localhost:8081/video/"+videoName;
                String url2="http://localhost:8081/img/"+imgName;
                video.setVideoUrl(url1);
                video.setImgUrl(url2);

                //将信息存入数据库中
                System.out.println("video中的信息为:"+video);
                int result=videoService.addVideo(video);
                System.out.println("视频插入完成信息："+result+"\n");

                //保存文件
                videoUrl.transferTo(video1);
                imgUrl.transferTo(img1);

            } catch (IOException e) {
                return "404";
            }



            return "ok";
        }
    }

    @RequestMapping("/changeType0")
    @ResponseBody
    public String changeType0(String replyId){
        replyService.changType0(replyId);
        return "ok";
    }

    @RequestMapping("/changeType2")
    @ResponseBody
    public String changeType2(String replyId,String userId){
        replyService.changType2(replyId);
        User user=userService.queryUserLevel(userId);
        String userLevel1=user.getUserLevel();
        int userLevel2=Integer.parseInt(userLevel1);
        userLevel2+=1;
        String userLevel3=String.valueOf(userLevel2);

        User user1=new User();
        user1.setUserId(userId);
        user1.setUserLevel(userLevel3);
        userService.updateUserLevel(user1);

        return "ok";
    }


    //去关键字的页面
    @RequestMapping("/keywords")
    public String keywords(HttpSession session){

        List<KeyWord> keyWords=keyWordService.queryAllKeyWords();
        session.setAttribute("keyword",keyWords);
        return "keywords";
    }


    @RequestMapping("/delKeyword")
    public void delKeyword(String keywordId){

//        KeyWord keyWord=new KeyWord();
//        keyWord.setKeyWordId(keywordId);

        keyWordService.delKeyWord(keywordId);

    }

    @RequestMapping("/addKeyWord")
    public void addKeyWord(String content){
        KeyWord keyWord=new KeyWord();
        keyWord.setAdminId("123");
        keyWord.setKeyWordContent(content);

        keyWordService.addKeyWord(keyWord);

    }

}
