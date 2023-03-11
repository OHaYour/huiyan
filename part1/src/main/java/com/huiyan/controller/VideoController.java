package com.huiyan.controller;

import com.huiyan.pojo.*;
import com.huiyan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoAppendService videoAppendService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private OrdersService ordersService;



    /**
     * 跳转到上传视频
     * @return
     */
    @RequestMapping("/toUploadVideo")
    public String toUploadVideo(){
        return "uploadVideo";
    }


    /**
     * 获取到首页发送过来的video信息，存入session，在详情页读出
     * @return
     */
    @RequestMapping("/setToVideo")
    @ResponseBody
    public String setToVideo(String videoId,HttpSession session){

//        System.out.println("接收到的imgPath:"+imgPath);
//        System.out.println("接收到的VideoName:"+videoPath);
//
//        model.addAttribute("imgName",imgPath);
//        model.addAttribute("videoName",videoPath);
//
//        List<Video> videos=new ArrayList<>();
//        Video video = new Video();
//        video.setImgPath(imgPath);
//        video.setVideoPath(videoPath);
//        videos.add(video);
//        model.addAttribute("vp",videos);
//
//        session.setAttribute("imgName",imgPath);
//        session.setAttribute("videoName",videoPath);

//        Video video1 = new Video();
//        video1.setImgPath(imgPath);
//        video1.setVideoPath(videoPath);
//        video1.setVideoId(videoId);

        //通过videoId获取到视频详情，用于视频播放时的视频信息展示
        Video video=new Video();
        video.setVideoId(videoId);
        Video video1=videoService.queryVideoInfo(video);

        //点击增加video的播放量
        String videoPlayAmount=video1.getVideoPlayAmount();
        int videoPlayAmount1=Integer.parseInt(videoPlayAmount);
        videoPlayAmount1+=1;
        String videoPlayAmount2=String.valueOf(videoPlayAmount1);
        video1.setVideoPlayAmount(videoPlayAmount2);
        videoService.changeVideoPlayAmount(videoId,videoPlayAmount2);

        session.setAttribute("videoPlay",video1);

        //通过获取到的userId获取到用户详情，用于视频播放时用户的信息展示
        User user=userService.queryUserByUserId(video1.getUserId());
        session.setAttribute("videoUser",user);

        //播放视频时读取点赞、收藏等信息
        User user1= (User) session.getAttribute("loginUser");
        String userId=user1.getUserId();
        VideoAppend videoAppend=new VideoAppend();
        videoAppend.setUserId(userId);
        videoAppend.setVideoId(video1.getVideoId());
        VideoAppend videoAppend1=videoAppendService.queryByUidVid(videoAppend);
        //若用户未观看过，则增加观看信息
        if (videoAppend1==null){
            videoAppend.setVideoLike("0");
            videoAppend.setVideoCoin("0");
            videoAppend.setVideoCollection("0");
            videoAppendService.addVideoUser(videoAppend);
            //将观看后的信息存入session
            session.setAttribute("videoAppend",videoAppend);
        }else{
            session.setAttribute("videoAppend",videoAppend1);
        }

        //查询出该视频下的所有评论回复
        List<Topic> topics=topicService.queryVideoTopic(videoId);
        List<Reply> replies=replyService.queryTopicReply(videoId);

        for (Topic topic: topics) {
            String tp1=topic.getTopicId();
            List<Reply>replys=new ArrayList<>();
            for (Reply reply: replies) {
                String tp2=reply.getTopicId();
                if (tp2.equals(tp1)){
                   replys.add(reply);
                }
            }
            topic.setReplys(replys);
        }

        System.out.println("调整后的评论回复为："+topics);

        session.setAttribute("topics",topics);
        session.setAttribute("replies",replies);


        return "ok";
    }

    @RequestMapping("/toVideo")
    public String toVideo(){
        return "video";
    }

    /**
     *加载首页视频图片
     * @return
     */
    @RequestMapping("/loadVideoImg")
    public String loadVideoImg(Model model,HttpSession session){

        System.out.println("进入了loadVideoImg方法");
        model.addAttribute("flag",true);
//        model.getAttribute("flag");

        //三条轮播图
        List<Video> videos1=videoService.queryVideosByVideoLike();
        model.addAttribute("videos1",videos1);

        //六条热门
        List<Video> videos2=videoService.queryVideosByVideoPlayAmount();
        model.addAttribute("videos2",videos2);
        //八条最新发布
        List<Video> videos3=videoService.queryVideosByUploadTime();
        model.addAttribute("videos3",videos3);
        //用于视频详情页的视频展示
        session.setAttribute("videos4",videos3);

        List<Video> users1=userService.queryUserByVideoId();
        session.setAttribute("user1",users1);

        return "index";
    }

    /**
     * 上传视频
     * @param videoTitle
     * @param videoInfo
     * @param videoUrl
     * @param imgUrl
     * @param videoTime
     * @param videoType
     * @return
     */
    @RequestMapping("/checkVideo")
    @ResponseBody
    public String checkVideo(String videoTitle,String videoInfo,@RequestParam("videoUrl") MultipartFile videoUrl,@RequestParam("imgUrl") MultipartFile imgUrl, String videoTime,String videoType,HttpSession session){

        User user= (User) session.getAttribute("loginUser");
        Video video=new Video();
        video.setVideoTitle(videoTitle);
        video.setVideoInfo(videoInfo);
        video.setVideoTime(videoTime);
        video.setVideoType(videoType);
        video.setVideoPlayAmount("0");
        video.setVideoLike("0");
        video.setVideoCoin("0");
        video.setVideoCollection("0");
        video.setUserId(user.getUserId());
        video.setVideoState("0");

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

    @RequestMapping("/toVideoGroup")
    public String toVideoGroup(){
        return "videoGroup";
    }

    @RequestMapping("/getVideosByType")
    @ResponseBody
    public String getVideosByType(String videoType,HttpSession session){

        List<Video> videos=videoService.queryVideoByVideoType(videoType);
        session.setAttribute("videoType",videos);
        return "ok";
    }

    @RequestMapping("/changeVideoLike")
    @ResponseBody
    public String changeVideoLike(String videoLike,String likeAmount,HttpSession session){

        //将string类型转换成int进行数量变化
        int likeAmount1=Integer.parseInt(likeAmount);

        if (videoLike.equals("0")){
            videoLike="1";
            likeAmount1+=1;
        }else if (videoLike.equals("1")){
            videoLike="0";
            likeAmount1-=1;
        }

        //将int转换成string存入数据库
        String likeAmount2=Integer.toString(likeAmount1);
        Video videoPlay = (Video) session.getAttribute("videoPlay");
        String videoId=videoPlay.getVideoId();
        videoService.changeVideoLikeAmount(videoId,likeAmount2);

        User videoUser= (User) session.getAttribute("loginUser");
        String userId=videoUser.getUserId();

        VideoAppend videoAppend=new VideoAppend();
        videoAppend.setVideoId(videoId);
        videoAppend.setUserId(userId);
        videoAppend.setVideoLike(videoLike);
        videoAppendService.changeLikeStatue(videoAppend);

        //通过videoId获取到视频详情，用于视频播放时的视频信息展示
        Video video=new Video();
        video.setVideoId(videoId);
        Video video1=videoService.queryVideoInfo(video);
        session.setAttribute("videoPlay",video1);

        //更新观看后的信息
        VideoAppend videoAppend1=videoAppendService.queryByUidVid(videoAppend);
        //将观看后的信息存入session
        session.setAttribute("videoAppend",videoAppend1);

        return "ok";
    }

    @RequestMapping("/changeVideoCoin")
    @ResponseBody
    public String changeVideoCoin(String videoCoin,String coinAmount,HttpSession session){

        Video videoPlay = (Video) session.getAttribute("videoPlay");
        String videoId=videoPlay.getVideoId();
        User loginUser= (User) session.getAttribute("loginUser");
        String userId=loginUser.getUserId();


        BigDecimal balance1=loginUser.getUserBalance();
        BigDecimal balance2=new BigDecimal(1);
        int res=balance1.compareTo(balance2);

        //余额不足
        if(String.valueOf(res).equals("-1")){
            return "balanceLow";
        } else if(String.valueOf(res).equals("1")){
            BigDecimal balance3=balance1.subtract(balance2);

            User user=new User();
            user.setUserId(loginUser.getUserId());
            user.setUserBalance(balance3);

            userService.updateUserByUserId(user);

            VideoAppend videoAppend=new VideoAppend();
            videoAppend.setVideoId(videoId);
            videoAppend.setUserId(userId);

            int coinAmount1=Integer.parseInt(coinAmount);

            //投币只能每次进行加一操作，不能改变状态
            if (videoCoin.equals("0")){
                videoCoin="1";

                videoAppend.setVideoCoin(videoCoin);
                videoAppendService.changeCoinStatus(videoAppend);
            }
            coinAmount1+=1;

            //添加订单
            //使用UUID作为order_number订单号
            UUID uuid = UUID.randomUUID();
            String uuid1 = uuid.toString();
            String order_number = uuid1.replaceAll("-", "");
            Orders orders=new Orders();
            orders.setOrderNo(order_number);
            orders.setUserId(userId);
            orders.setTradeType("投币");
            orders.setTotalAmount(new BigDecimal(1));
            orders.setStatus("1");
            orders.setCreateTime(new Date());
            ordersService.addOrder(orders);

            //将int转换成string存入数据库
            String coinAmount2=Integer.toString(coinAmount1);
            videoService.changeVideoCoinAmount(videoId,coinAmount2);


            //通过videoId获取到视频详情，用于视频播放时的视频信息展示
            Video video=new Video();
            video.setVideoId(videoId);
            Video video1=videoService.queryVideoInfo(video);
            session.setAttribute("videoPlay",video1);

            //更新观看后的信息
            VideoAppend videoAppend1=videoAppendService.queryByUidVid(videoAppend);
            //将观看后的信息存入session
            session.setAttribute("videoAppend",videoAppend1);

            //更新投币后的用户信息
            User user2=userService.queryUserByUserId(userId);
            session.setAttribute("loginUser",user2);

            return "ok";

        }else {
            return "error";
        }
    }

    @RequestMapping("/changeVideoCollection")
    @ResponseBody
    public String changeVideoCollection(String videoCollection,String collectionAmount,HttpSession session){

        Video videoPlay = (Video) session.getAttribute("videoPlay");
        String videoId=videoPlay.getVideoId();
        User videoUser= (User) session.getAttribute("loginUser");
        String userId=videoUser.getUserId();
        VideoAppend videoAppend=new VideoAppend();
        videoAppend.setVideoId(videoId);
        videoAppend.setUserId(userId);

        int collectionAmount1=Integer.parseInt(collectionAmount);

        if (videoCollection.equals("0")){
            videoCollection="1";
            collectionAmount1+=1;
        }else if (videoCollection.equals("1")){
            videoCollection="0";
            collectionAmount1-=1;
        }

        videoAppend.setVideoCollection(videoCollection);
        videoAppendService.changeCollectionStatus(videoAppend);

        String collectionAmount2=Integer.toString(collectionAmount1);
        videoService.changeVideoCollectionAmount(videoId,collectionAmount2);

        //通过videoId获取到视频详情，用于视频播放时的视频信息展示
        Video video=new Video();
        video.setVideoId(videoId);
        Video video1=videoService.queryVideoInfo(video);
        session.setAttribute("videoPlay",video1);

        //更新观看后的信息
        VideoAppend videoAppend1=videoAppendService.queryByUidVid(videoAppend);
        //将观看后的信息存入session
        session.setAttribute("videoAppend",videoAppend1);


        return "ok";
    }

    @RequestMapping("/searchVideoInfo")
    @ResponseBody
    public String searchVideoInfo(String searchInfo,HttpSession session){

        List<Video> videos=videoService.searchVideoInfo(searchInfo);
        session.setAttribute("videoType",videos);
        return "ok";
    }




}
