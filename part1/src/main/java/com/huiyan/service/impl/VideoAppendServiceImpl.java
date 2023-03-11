package com.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.huiyan.dao.VideoAppendDao;
import com.huiyan.pojo.VideoAppend;
import com.huiyan.service.VideoAppendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoAppendServiceImpl implements VideoAppendService {

    @Autowired
    private VideoAppendDao videoAppendDao;


    @Override
    public void addVideoUser(VideoAppend videoAppend) {

        videoAppendDao.insert(videoAppend);

    }

    @Override
    public VideoAppend queryByUidVid(VideoAppend videoAppend) {

        LambdaQueryWrapper<VideoAppend> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(VideoAppend::getVideoId,videoAppend.getVideoId()).eq(VideoAppend::getUserId,videoAppend.getUserId());
        VideoAppend videoAppend1=videoAppendDao.selectOne(wrapper);
        return videoAppend1;
    }

    @Override
    public void changeLikeStatue(VideoAppend videoAppend) {
        LambdaUpdateWrapper<VideoAppend> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(VideoAppend::getVideoId,videoAppend.getVideoId()).eq(VideoAppend::getUserId,videoAppend.getUserId())
                .set(VideoAppend::getVideoLike,videoAppend.getVideoLike());
        videoAppendDao.update(null,wrapper);
    }

    @Override
    public void changeCoinStatus(VideoAppend videoAppend) {
        LambdaUpdateWrapper<VideoAppend> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(VideoAppend::getVideoId,videoAppend.getVideoId()).eq(VideoAppend::getUserId,videoAppend.getUserId())
                .set(VideoAppend::getVideoCoin,videoAppend.getVideoCoin());
        videoAppendDao.update(null,wrapper);
    }

    @Override
    public void changeCollectionStatus(VideoAppend videoAppend) {
        LambdaUpdateWrapper<VideoAppend> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(VideoAppend::getVideoId,videoAppend.getVideoId()).eq(VideoAppend::getUserId,videoAppend.getUserId())
                .set(VideoAppend::getVideoCollection,videoAppend.getVideoCollection());
        videoAppendDao.update(null,wrapper);
    }
}
