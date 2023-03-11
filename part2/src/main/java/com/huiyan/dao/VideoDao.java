package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.User;
import com.huiyan.pojo.Video;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface VideoDao extends BaseMapper<Video> {


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
     * @param user
     */
    void addVideoAdvidce(Video video);
}
