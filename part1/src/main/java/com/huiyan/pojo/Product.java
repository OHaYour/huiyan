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

@TableName("product")
public class Product {

    //主键，唯一标识
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //产品id
    private String productId;

    //产品类型
    private String productType;

    //产品名称
    private String productName;

    //产品数量
    private String productAmount;

    //产品图片
    private String productImg;

    //产品状态
    private String productStatus;

}
