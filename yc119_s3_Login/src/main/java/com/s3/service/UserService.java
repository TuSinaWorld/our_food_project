package com.s3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.s3.bean.MemberInfoBean;
import com.s3.bean.Resuser;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<MemberInfoBean>{

    MemberInfoBean login(Resuser resuser);


    Integer logon(Resuser resuser, HttpServletRequest request);
}
