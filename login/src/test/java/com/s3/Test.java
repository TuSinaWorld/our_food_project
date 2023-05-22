package com.s3;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.s3.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class Test {
    @Autowired(required = false)
    private UserMapper userMapper;


    //添加
    @org.junit.jupiter.api.Test
    public void logon(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String createTime = dateFormat.format(now);//格式化然后放入字符串中
        Integer integer = userMapper.saveLogon("zzw1", "1", "1", createTime);
        System.out.println(integer);
    }

    //登入
    @org.junit.jupiter.api.Test
    public void login(){
        LambdaQueryWrapper<MemberInfoBean> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(MemberInfoBean::getNickName,"zzw").eq(MemberInfoBean::getPwd,1);
        MemberInfoBean result = userMapper.selectOne(wrapper);
        System.out.println(result.toString());
    }
}
