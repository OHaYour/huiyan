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

@TableName("games")
public class Games {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //游戏id
    private String gameId;

    //游戏最佳时间
    private String bestTime;

    //游戏类型
    private String gameType;

    //用户id
    private String userId;

    //游戏最佳成绩
    private String bestGrade;



}
