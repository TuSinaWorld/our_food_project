package com.s3.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.s3.bean.MemberInfoBean;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends BaseMapper<MemberInfoBean> {

    public Integer saveLogon(@Param("nickName") String nickName,@Param("pwd") String pwd,@Param("email") String email,@Param("regDate") String regDate);

}
