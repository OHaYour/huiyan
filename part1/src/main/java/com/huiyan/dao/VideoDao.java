package com.huiyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huiyan.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface VideoDao extends BaseMapper<Video> {

    /**
     * 通过用户点赞数排出前三个轮播视频
     * @return
     */
    List<Video> queryVideosByVideoLike();


    /**
     * 通过播放量排前六热门视频
     * @return
     */
    List<Video> queryVideosByVideoPlayAmount();

    /**
     * 通过上传时间排出前八最新发布
     * @return
     */
    List<Video> queryVideosByUploadTime();

    /**
     * 通过条件查询出单个视频的信息
     * @param videoId
     * @return
     */
    Video queryVideoInfo(String videoId);

    /**
     * 通过videoType查询所有的video对象
     * @param videoType
     * @return
     */
    List<Video> queryVideoByVideoType(String videoType);

    /**
     * 修改视频的点赞数量
     * @param videoLike
     */
    void changeVideoLikeAmount(String videoId,String videoLike);

    /**
     * 修改视频的投币数量
     * @param videoId
     * @param videoCoin
     */
    void changeVideoCoinAmount(String videoId,String videoCoin);

    /**
     * 修改视频的收藏数量
     * @param videoId
     * @param videoCollection
     */
    void changeVideoCollectionAmount(String videoId,String videoCollection);

    /**
     * 搜索获取视频信息
     * @param searchInfo
     * @return
     */
    List<Video> searchVideoInfo(String searchInfo);

    /**
     * 增加视频播放量
     * @param videoId
     * @param videoPlayAmount
     */
    void changeVideoPlayAmount(String videoId,String videoPlayAmount);
}
