package com.s3.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.s3.bean.AdminInfo;
import com.s3.bean.Result;
import com.s3.bean.UserInfo;
import com.s3.dao.UserInfoDao;
import io.jsonwebtoken.Claims;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserInfoDao userInfoDao;

    @RequestMapping("/login")
    public Result login(@RequestParam String level,@RequestParam String name,@RequestParam String password){
        try {
            //判断name或password是否为空
            if (name == null || password == null){
                return Result.failure("空字符串",null);
            }
            //
            UserInfo userInfo = userInfoDao.selectOne(
                    new QueryWrapper<UserInfo>().eq("name",name).eq("password",password));
            //如果查询出的user类中没有相匹配user则说明输入name或password错误
            if (userInfo == null){
                return Result.failure("账号或密码错误...",null);
            }

            //subject 存的mno claim 存的是对象 ,  只将Id存入token中
            String token = JwtUtil.createJWT(userInfo.getId().toString(), userInfo.getId().toString(), null);
            redisTemplate.opsForValue().set(LOGIN_USER+userInfo.getId(),token,LOGIN_USER_TTL, TimeUnit.MINUTES);
            Claims claims = JwtUtil.parseJWT(token);
            return Result.success("用户登录成功 ...",token);
        }catch (RuntimeException e){
            logger.error(e.getMessage());
            return Result.failure("错误...",e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/checklogin")
    public Result CheckLogin(@RequestHeader String token)  {
        //判断前端是否传来token或token为空值时
        if(token == null || "".equals(token)){
            return Result.failure("用户未登录 ...",null);
        }
        //定义id
        String id;
        try{
            //读取token中唯一的内容 用户Id
            Claims claims = JwtUtil.parseJWT(token);
            id = claims.get("user").toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        if (id == null || "".equals(id)){
            return Result.failure("用户未登录 ...",null);
        }
        //根据id查询数据库中用户信息
        UserInfo userInfo = userInfoDao.selectOne(new QueryWrapper<UserInfo>().eq("id", id));
        if(userInfo == null){
            return Result.failure("用户未登录 ...",null);
        }
        //修改密码，防止密码暴露
        userInfo.setPassword("*****");
        return Result.success("用户已登陆",userInfo);
    }
}
