package com.s3.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.s3.bean.MemberInfoBean;
import com.s3.bean.Resuser;
import com.s3.mapper.UserMapper;
import com.s3.service.UserService;
import com.s3.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper,MemberInfoBean> implements UserService {
    @Autowired
    private UserService userService;
    @Autowired
    private Md5 md5;

    @Override
    public MemberInfoBean login(Resuser resuser) {
        LambdaQueryWrapper<MemberInfoBean> wrapper =new LambdaQueryWrapper<>();

        String pwd = md5.MD5Encode(resuser.getPwd());
        resuser.setPwd(pwd);

        wrapper.eq(MemberInfoBean::getNickName,resuser.getNickName()).eq(MemberInfoBean::getPwd,resuser.getPwd());
        MemberInfoBean result = userService.getOne(wrapper);

        //判断账号 密码是否对
       if(StringUtils.isEmpty(result)){
            throw new RuntimeException("账号或密码不正确");
       }
        //TODO 验证码
        return result;
    }

    @Override
    public MemberInfoBean logon() {
        return null;
    }
}
