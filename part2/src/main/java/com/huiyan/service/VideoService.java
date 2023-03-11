package com.huiyan.service;

import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;

import java.util.List;

public interface VideoService {

    /**
     * 查询近30天上传视频的数量
     * @return
     */
    String queryVideoAmount();

    /**
     * 查询所有状态为待审核的video
     * @return
     */
    List<Video> queryAllVideos();

    /**
     * 通过videoId查询video对象
     * @return
     */
    Video queryVideoByVideoId(String videoId);

    /**
     * 修改视频状态
     * @param videoId
     */
    void updateVideoState(String videoId);

    /**
     * 审核未通过添加修改意见
     * @param video
     */
    void addVideoAdvidce(Video video);

    /**
     * 添加一条视频的信息
     * @param video
     * @return
     */
    int addVideo(Video video);

}
