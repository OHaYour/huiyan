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

@TableName("reply")
public class Reply {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //评论id
    private String topicId;

    //回复id
    private String replyId;

    //回复类型
    private String replyType;

    //回复内容
    private String content;

    //回复的用户名
    private String fromUserNickName;

    //回复的用户id
    private String fromUid;

    //回复的视频id
    private String fromVid;

    //创建回复的时间
    private Date topicTime;

    //评论的用户名
    private String toUserNickName;

    //评论的用户id
    private String toUid;

    //特殊标记
    private String replyFlag;

    //ip地址
    private String ipAdress;



}
