package com.ourfood.background.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourfood.background.bean.Result;
import com.ourfood.background.dao.AdminInfoDao;
import com.s3.bean.AdminInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.JwtUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.ourfood.background.util.Redisconstants.LOGIN_ADMIN;
import static com.ourfood.background.util.Redisconstants.LOGIN_ADMIN_TTL;

@RequestMapping("/background")
@RestController
@Slf4j
public class BackController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    AdminInfoDao adminInfoDao;

    @RequestMapping("/login")
    public Result login(@RequestParam String name, @RequestParam String password){
        try {
            //判断name或password是否为空
            if (name == null || password == null){
                return Result.failure("空字符串",null);
            }
            //
            AdminInfo adminInfo = adminInfoDao.selectOne(
                    new QueryWrapper<AdminInfo>().eq("name",name).eq("password",password));
            //如果查询出的user类中没有相匹配user则说明输入name或password错误
            if (adminInfo == null){
                return Result.failure("账号或密码错误...",null);
            }

            //subject 存的mno claim 存的是对象 ,  只将Id存入token中
            String token = JwtUtil.createJWT(adminInfo.getId().toString(), adminInfo.getId().toString(), null);
            redisTemplate.opsForValue().set(LOGIN_ADMIN + adminInfo.getId(),token,LOGIN_ADMIN_TTL, TimeUnit.MINUTES);
            Claims claims = JwtUtil.parseJWT(token);
            return Result.success("管理员登录成功 ...",token);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return Result.failure("登录错误...",e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/checkLogin")
    public Result CheckLogin(@RequestHeader String admin)  {
        //判断前端是否传来token或token为空值时
        if(admin == null || "".equals(admin)){
            return Result.failure("管理员未登录 ...",null);
        }
        //定义id
        String id;
        try{
            //读取token中唯一的内容 用户Id
            Claims claims = JwtUtil.parseJWT(admin);
            id = claims.get("user").toString();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        if (id == null || "".equals(id)){
            return Result.failure("管理员未登录 ...",null);
        }
        //根据id查询数据库中用户信息
        AdminInfo adminInfo = adminInfoDao.selectOne(new QueryWrapper<AdminInfo>().eq("id", id));
        if(adminInfo == null){
            return Result.failure("管理员未登录 ...",null);
        }
        //修改密码，防止密码暴露
        adminInfo.setPassword("*****");
        return Result.success("管理员已登陆",adminInfo);
    }

    @RequestMapping("/showAdminsInfo.admin")
    public Result showAdminsInfo(){
        List<AdminInfo> adminInfos = null;
        try {
            adminInfos = adminInfoDao.selectList(null);
            adminInfos.forEach(adminInfo -> {
                adminInfo.setPassword("***");
             });
            return Result.success("查询成功",adminInfos);
        }catch (Exception e){
            log.error("查询失败" + e.getMessage());
            return Result.failure("查询失败",null);
        }
    }
}
