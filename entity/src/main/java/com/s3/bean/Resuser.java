package com.s3.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class Resuser implements Serializable {
    private String nickName;
    private String pwd;
    private String pwd1; //重复的密码
    private String email;
    private String port;  //验证码
    private Integer mno;  //编号
    private Integer status; //1和0值

    @Override
    public String toString() {
        return "Resuser{" +
                "nickName='" + nickName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", port='" + port + '\'' +
                ", mno=" + mno +
                ", status=" + status +
                '}';
    }
}
