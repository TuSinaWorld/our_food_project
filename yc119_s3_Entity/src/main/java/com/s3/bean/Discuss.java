package com.s3.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mqb
 * @Date: 2022/11/15
 * @Time: 20:43
 * @Description:
 */
@Data
public class Discuss implements Serializable {
    private Integer did;
    private Integer mno;
    private Integer gno;
    private String dis;
    private String publishtime;
    private String nickName="";


}
