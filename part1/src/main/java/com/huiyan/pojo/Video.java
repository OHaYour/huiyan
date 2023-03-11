package com.huiyan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName("video")
public class Video extends User{

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //视频的id
    private String videoId;

    //视频标题
    private String videoTitle;

    //视频时长
    private String videoTime;

    //视频存放路径
    private String videoPath;

    //视频上传路径
    private String videoUrl;

    //视频简介
    private String videoInfo;

    //视频封面
    private String imgPath;

    //封面路径
    private String imgUrl;

    //视频上传时间
    private Date uploadTime;

    //视频播放量
    private String videoPlayAmount;

    //视频点赞量
    private String videoLike;

    //视频投币量
    private String videoCoin;

    //视频收藏量
    private String videoCollection;

    //视频类型
    private String videoType;

    //上传者的id
    private String userId;

    //视频状态
    private String videoState;

    //视频备注
    private String videoNote;

}
