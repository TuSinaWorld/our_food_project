package com.s3.service.Impl;


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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper,MemberInfoBean> implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private Md5 md5;
    @Autowired(required = false)
    private MemberInfoBean memberInfoBean;

    @Override
    public MemberInfoBean login(Resuser resuser) {
        LambdaQueryWrapper<MemberInfoBean> wrapper =new LambdaQueryWrapper<>();

        String pwd = md5.MD5Encode(resuser.getPwd(),"utf-8");
        resuser.setPwd(pwd);

        wrapper.eq(MemberInfoBean::getNickName,resuser.getNickName()).eq(MemberInfoBean::getPwd,resuser.getPwd());
        MemberInfoBean result = userMapper.selectOne(wrapper);

        //判断账号 密码是否对
       if(StringUtils.isEmpty(result)){
            throw new RuntimeException("账号或密码不正确");
       }
        return result;
    }

    @Override
    public Integer logon(Resuser resuser) {
        String pwd1 = resuser.getPwd1();
        resuser.setPwd(resuser.getPwd().trim());
        if (  resuser.getNickName()==null || resuser.getPwd()==null || resuser.getEmail()==null){
           throw new RuntimeException("不能为空");
        }

        if (resuser.getNickName().length()<5||resuser.getNickName().length()>20){
            throw new RuntimeException("请输入5-20个字符的用户名");
        }
        if(!resuser.getPwd().equals(pwd1)){
            throw new RuntimeException("前后密码不一致");
        }
        if (resuser.getPwd().length()<8||resuser.getPwd().length()>20){
            throw new RuntimeException("密码最少8位，最长20位");
        }
        if (!checkEmail(resuser.getEmail())){
            throw new RuntimeException("邮箱格式不正确");
        }
        //添加当前时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String createTime = dateFormat.format(now);//格式化然后放入字符串中
        String pwd=(Md5.MD5Encode(resuser.getPwd(),"utf-8")); //加密

        Integer result = userMapper.saveLogon(resuser.getNickName(), pwd, resuser.getEmail(), createTime);
        if(result<0){
            throw new RuntimeException("添加失败");
        }
        return result;
    }

    //验证邮箱
    public static boolean checkEmail(String email)
    {String str = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:\\w(?:[\\w-]*\\w)?\\.)+\\w(?:[\\w-]*\\w)?";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
