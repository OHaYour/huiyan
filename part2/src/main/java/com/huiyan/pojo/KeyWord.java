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

@TableName("keyWord")
public class KeyWord {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //关键字的id
    private String keyWordId;

    //关键字的内容
    private String keyWordContent;

    //管理员的id
    private String adminId;
}
