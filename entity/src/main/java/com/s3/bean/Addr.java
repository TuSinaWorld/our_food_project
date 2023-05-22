package com.s3.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;

@Data
@TableName("addrinfo")
public class Addr implements Serializable {
    @TableId(type = IdType.AUTO)
    private String ano;
    private int mno;
    private String name;
    private String tel;
    private String province;
    private String city;
    private String area;
    private String addr;
    private int flag;
    private int status;
}
