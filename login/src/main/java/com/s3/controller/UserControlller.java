package com.s3.controller;


import com.s3.bean.MemberInfoBean;
import com.s3.bean.UserInfo;
import com.s3.common.Result;
import com.s3.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;


import java.util.concurrent.TimeUnit;

import static com.s3.utils.Redisconstants.LOGIN_USER;
import static com.s3.utils.Redisconstants.LOGIN_USER_TTL;

@RestController
@RequestMapping("/user")
public class UserControlller {
    @Autowired(required = false)
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result Login(@RequestBody UserInfo resuser){
        try {
            MemberInfoBean result = userService.login(resuser);
            //subject 存的mno claim 存的是对象

            String token = JwtUtil.createJWT(result, result.getMno().toString(), null);
            redisTemplate.opsForValue().set(LOGIN_USER+result.getMno(),token,LOGIN_USER_TTL, TimeUnit.MINUTES);

            return Result.success(result);
        }catch (RuntimeException e){
            return Result.fail(e.getMessage());
        }
    }



    @GetMapping("/logout/{mno}" )
    public Result Logout(@PathVariable("mno") String mno){
        //退出 删除 redis中的token
        redisTemplate.delete(LOGIN_USER + mno);
        return Result.fail("退出成功");
    }

    @PostMapping("/logon")
    public Result Logon(@RequestBody UserInfo resuser){
        try {
            Integer result = userService.logon(resuser);
            return  Result.success("添加成功");
        }catch (Exception e){
            return  Result.fail(e.getMessage());
        }
    }
}
