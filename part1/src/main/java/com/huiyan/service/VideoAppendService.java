package com.huiyan.service;

import com.huiyan.pojo.VideoAppend;

public interface VideoAppendService {

    //观看视频后添加视频与用户的关系
    void addVideoUser(VideoAppend videoAppend);

    //查看用户是否有观看记录
    VideoAppend queryByUidVid(VideoAppend videoAppend);

    //修改点赞状态
    void changeLikeStatue(VideoAppend videoAppend);

    //修改投币状态
    void changeCoinStatus(VideoAppend videoAppend);

    //修改收藏状态
    void changeCollectionStatus(VideoAppend videoAppend);


    
}
