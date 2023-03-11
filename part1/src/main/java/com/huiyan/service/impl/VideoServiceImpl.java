package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huiyan.dao.VideoDao;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import com.huiyan.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

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

    @Override
    public List<Video> queryVideosByVideoLike() {
        List<Video> videos1=videoDao.queryVideosByVideoLike();
        return videos1;
    }

    @Override
    public List<Video> queryVideosByVideoPlayAmount() {
        List<Video> videos2=videoDao.queryVideosByVideoPlayAmount();
        return videos2;
    }

    @Override
    public List<Video> queryVideosByUploadTime() {
        List<Video> videos3=videoDao.queryVideosByUploadTime();
        return videos3;
    }

    @Override
    public Video queryVideoInfo(Video video) {

        String videoId=video.getVideoId();
        Video video1=videoDao.queryVideoInfo(videoId);
        return video1;
    }

    @Override
    public List<Video> queryVideoByVideoType(String videoType) {
        List<Video> videos=videoDao.queryVideoByVideoType(videoType);
        return videos;
    }

    @Override
    public void changeVideoLikeAmount(String videoId, String videoLike) {
        videoDao.changeVideoLikeAmount(videoId,videoLike);
    }

    @Override
    public void changeVideoCoinAmount(String videoId, String videoCoin) {
        videoDao.changeVideoCoinAmount(videoId,videoCoin);
    }

    @Override
    public void changeVideoCollectionAmount(String videoId, String videoCollection) {
        videoDao.changeVideoCollectionAmount(videoId,videoCollection);
    }

    @Override
    public List<Video> searchVideoInfo(String searchInfo) {

        List<Video> videos=videoDao.searchVideoInfo(searchInfo);
        return videos;
    }

    @Override
    public void changeVideoPlayAmount(String videoId, String videoPlayAmount) {
        videoDao.changeVideoPlayAmount(videoId,videoPlayAmount);
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
