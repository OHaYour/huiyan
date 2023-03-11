package com.huiyan.service.impl;

import com.huiyan.dao.VideoDao;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;


    @Override
    public String queryVideoAmount() {
        String videoNum=videoDao.queryVideoAmount();
        return videoNum;

    }

    @Override
    public List<Video> queryAllVideos() {
        List<Video> videos=videoDao.queryAllVideos();
        return videos;
    }

    @Override
    public Video queryVideoByVideoId(String videoId) {
        Video video=videoDao.queryVideoByVideoId(videoId);
        return video;
    }

    @Override
    public void updateVideoState(String videoId) {
        videoDao.updateVideoState(videoId);
    }

    @Override
    public void addVideoAdvidce(Video video) {
        videoDao.addVideoAdvidce(video);
    }

    @Override
    public int addVideo(Video video) {

        //获取当前时间的毫秒数,插入videoId
        Date date = new Date();
        long dateTime=date.getTime();
        System.out.println("当前时间的毫秒数："+dateTime);
        //将时间的毫秒数转为string类型
        String randomNum=String.valueOf(dateTime);
        String videoId=randomNum.substring(randomNum.length()-8,randomNum.length());

        video.setVideoId(videoId);
        video.setUploadTime(date);

        videoDao.insert(video);

        return 1;
    }

//    @Override
//    public List<Video> queryVideosByVideoLike() {
//
//        QueryWrapper<Video> wrapper =new QueryWrapper<>();
//        wrapper.orderByDesc("videoLike");
//
//        List<Video> videos1=videoDao.selectList(wrapper);
//        List<Video> videos2=new ArrayList<>();
//
//        //从获取到的所有video对象截取出前三个
//        for (int i=0;i<3;i++){
//            //更改imgPath为:/img/imgName 在前端页面直接读出
//            //更改videoPath为:/video/videoName 在前端页面直接读出
//            Video v=videos1.get(i);
//            String imgPath1=v.getImgPath();
//            String videoPath1=v.getVideoPath();
//            String imgPath2="/img/"+imgPath1.substring(imgPath1.lastIndexOf("/")+1);
//            String videoPath2="/video/"+videoPath1.substring(videoPath1.lastIndexOf("/")+1);
//            v.setImgPath(imgPath2);
//            v.setVideoPath(videoPath2);
//            videos1.set(i,v);
//
//            videos2.add(videos1.get(i));
//        }
//
//        return videos2;
//    }
//
//    @Override
//    public List<Video> queryVideosByVideoPlayAmount() {
//        QueryWrapper<Video> wrapper =new QueryWrapper<>();
//        wrapper.orderByDesc("videoPlayAmount");
//
//        List<Video> videos1=videoDao.selectList(wrapper);
//        List<Video> videos2=new ArrayList<>();
//
//        for (int i=0;i<6;i++){
//            //更改imgPath为:/img/imgName 在前端页面直接读出
//            //更改videoPath为:/video/videoName 在前端页面直接读出
//            Video v=videos1.get(i);
//            String imgPath1=v.getImgPath();
//            String videoPath1=v.getVideoPath();
//            String imgPath2="/img/"+imgPath1.substring(imgPath1.lastIndexOf("/")+1);
//            String videoPath2="/video/"+videoPath1.substring(videoPath1.lastIndexOf("/")+1);
//            v.setImgPath(imgPath2);
//            v.setVideoPath(videoPath2);
//            videos1.set(i,v);
//
//            videos2.add(videos1.get(i));
//        }
//
//        return videos2;
//    }
//
//    @Override
//    public List<Video> queryVideosByUploadTime() {
//        QueryWrapper<Video> wrapper =new QueryWrapper<>();
//        wrapper.orderByDesc("uploadTime");
//
//        List<Video> videos1=videoDao.selectList(wrapper);
//        List<Video> videos2=new ArrayList<>();
//
//        for (int i=0;i<8;i++){
//
//            //更改imgPath为:/img/imgName 在前端页面直接读出
//            //更改videoPath为:/video/videoName 在前端页面直接读出
//            Video v=videos1.get(i);
//            String imgPath1=v.getImgPath();
//            String videoPath1=v.getVideoPath();
//            String imgPath2="/img/"+imgPath1.substring(imgPath1.lastIndexOf("/")+1);
//            String videoPath2="/video/"+videoPath1.substring(videoPath1.lastIndexOf("/")+1);
//            v.setImgPath(imgPath2);
//            v.setVideoPath(videoPath2);
//            videos1.set(i,v);
//
//            videos2.add(videos1.get(i));
//        }
//
//        return videos2;
//    }
}
