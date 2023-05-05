package com.s3;

import com.s3.bean.MemberInfoBean;
import com.s3.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class Test {
    @Autowired
    private UserMapper userMapper;

    @org.junit.jupiter.api.Test
    public void test(){
        MemberInfoBean memberInfoBean = userMapper.selectById(1);
        System.out.println(memberInfoBean.toString());
    }
}
