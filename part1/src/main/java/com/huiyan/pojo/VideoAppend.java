package com.huiyan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName("videoAppend")
public class VideoAppend {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //视频id
    private String videoId;

    //用户id
    private String userId;

    //视频点赞
    private String videoLike;

    //视频收藏
    private String videoCoin;

    //视频收藏
    private String videoCollection;

}
