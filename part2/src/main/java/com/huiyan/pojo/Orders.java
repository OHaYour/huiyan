package com.huiyan.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //订单编号
    private String orderNo;

    //订单种类
    private String tradeType;

    //订单金额
    private BigDecimal totalAmount;

    //订单状态
    private String status;

    //创建订单的userId
    private String userId;

    //创建订单的时间
    private Date createTime;
}
