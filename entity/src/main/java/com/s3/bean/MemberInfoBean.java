package com.s3.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


//用户表
@Data
@TableName("memberinfo")
public class MemberInfoBean {
    @TableId(type = IdType.AUTO)
    private Integer mno;
    @TableField("nickName")
    private String nickName;
    @TableField("realName")
    private String realName;
    private String pwd;
    private String tel;
    private String email;
    private String photo;
    @TableField("regDate")
    private String regDate;
    private Integer status;
//    private boolean ban = false;
}
