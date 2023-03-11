package com.huiyan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@TableName("user")


public class User {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //用户Id
    private String userId;

    //昵称
    private String userNickName;

    //真实姓名
    private String userRealName;

    //密码
    private String userPassword;

    //性别
    private String userSex;

    //等级
    private String userLevel;

    //账户余额
    private BigDecimal userBalance;

    //用户权限
    private String userPerms;

    //电话
    private String userPhone;

    //邮箱
    private String userEmail;

    //地址（调用百度地图接口）
    private String userAddress;

    //头像
    private String userImg;

    private String userImgUrl;

    //身份证
    private String userRealId;


}
