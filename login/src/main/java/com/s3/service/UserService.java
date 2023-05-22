package com.s3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.s3.bean.MemberInfoBean;
import com.s3.bean.UserInfo;

public interface UserService extends IService<MemberInfoBean >{

    MemberInfoBean login(UserInfo resuser);


    Integer logon(UserInfo resuser);
}
