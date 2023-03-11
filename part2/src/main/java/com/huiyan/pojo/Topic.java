package com.huiyan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName("topic")
public class Topic extends User {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //评论的id
    private String topicId;

    //评论的类型
    private String topicType;

    //评论的内容
    private String content;

    //评论发布的时间
    private Date topicTime;

    //评论发布者
    private String fromUid;

    //评论来源的视频
    private String fromVid;

    //当前评论下的所有回复
    private List<Reply> replys;
}
