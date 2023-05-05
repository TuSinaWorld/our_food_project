package com.s3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.s3.bean.MemberInfoBean;
import com.s3.bean.Resuser;

public interface UserService extends IService<MemberInfoBean>{

    MemberInfoBean login(Resuser resuser);


    MemberInfoBean logon();
}
