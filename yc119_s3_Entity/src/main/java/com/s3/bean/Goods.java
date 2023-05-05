package com.s3.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mqb
 * @Date: 2022/11/8
 * @Time: 12:08
 * @Description:
 */
@Data
public class Goods implements Serializable {
    private Integer gno; //商品号 主键
    private String gname;//商品名字
    private Integer tno;//商品所放架子号
    private String price;//价格
    private String intro;//商品描述
    private Integer balance;//
    private String pics;//商品图片位置
    private String unit;//数量单位
    private String qperied;//保质期
    private String weight;//重量数量
    private String descr;//描述


}
