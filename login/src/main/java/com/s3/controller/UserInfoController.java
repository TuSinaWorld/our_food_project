package com.s3.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.s3.bean.AdminInfo;
import com.s3.bean.Result;
import com.s3.bean.UserInfo;
import com.s3.dao.UserInfoDao;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.JwtUtil;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.s3.util.Redisconstants.LOGIN_USER;
import static com.s3.util.Redisconstants.LOGIN_USER_TTL;

/**
 * 用户管理
 */

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserInfoDao userInfoDao;

    @RequestMapping("/login")
    public Result login(@RequestParam String level,@RequestParam String name,@RequestParam String password){
        try {
            System.out.println(name + "    " + password + "      " + level);
            //判断name或password是否为空
            if (name == null || password == null){
                return Result.failure("空字符串",null);
            }
            //
            UserInfo userInfo = userInfoDao.selectOne(
                    new QueryWrapper<UserInfo>().eq("name",name).eq("password",password));
            System.out.println(userInfo);
            //如果查询出的user类中没有相匹配user则说明输入name或password错误
            if (userInfo == null){
                return Result.failure("账号或密码错误...",null);
            }

            //subject 存的mno claim 存的是对象

            String token = JwtUtil.createJWT(userInfo, userInfo.getId().toString(), null);
            redisTemplate.opsForValue().set(LOGIN_USER+userInfo.getId(),token,LOGIN_USER_TTL, TimeUnit.MINUTES);

            return Result.success("用户登录成功 ...",userInfo);
        }catch (RuntimeException e){
            throw e;
//            return Result.failure("错误...",e.getMessage());
        }
    }



}
