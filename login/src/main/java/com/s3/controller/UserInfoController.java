package com.s3.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.PreparedQuery;
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

    /**
     * 用户登录功能
     * @param level 用户身份
     * @param name 用户名
     * @param password 以后慢慢
     * @return
     */
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

    /**
     * 检测用户是否登录
     * @param token 前端Token
     * @return
     */
    @RequestMapping("/checkLogin")
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



    /**
     * 用户注册功能
     * @param level 身份信息
     * @param name 用户名
     * @param password 用户密码
     * @param sex 用户性别
     * @return
     */
    @RequestMapping ("/register")
    public Result Regist(@RequestParam String level,@RequestParam String name,@RequestParam String password,@RequestParam String sex){
        //判断用户名是否被注册
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        UserInfo userInfo1 = userInfoDao.selectOne(queryWrapper);
        if (userInfo1 != null){
            return Result.failure("用户名已经被注册 ...",null);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setPassword(password);
        userInfo.setSex(sex);
        userInfoDao.insert(userInfo);
        return Result.success("用户注册成功 ...",null);
    }

    /**
     * 重置密码
     * @param name 用户名
     * @param level 用户身份
     * @return
     */
    @RequestMapping("/resetPassword")
    public Result ResetPassword(@RequestParam String name,@RequestParam String level){
        //条件查询用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        UserInfo userInfo = userInfoDao.selectOne(queryWrapper);
        //判断该用户是否存在
        if (userInfo == null){
            return Result.failure("不存在该用户 ...",null);
        }
        //将密码重置为123456
        userInfo.setPassword("123456");
        userInfo.getId();
        userInfoDao.updateById(userInfo);

        return Result.success("重置密码成功...密码为:123456...",userInfo);
    }
}
