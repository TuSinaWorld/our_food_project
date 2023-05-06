package com.s3.controller;


import com.s3.bean.MemberInfoBean;
import com.s3.bean.Resuser;
import com.s3.common.Result;
import com.s3.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserControlller {
    @Autowired(required = false)
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("login")
    public Result Login(@RequestBody Resuser resuser){
        try {
            MemberInfoBean result = userService.login(resuser);
            //TODO token
            return Result.success(result);
        }catch (RuntimeException e){
            return Result.fail(e.getMessage());
        }
    }



    @GetMapping("logout")
    public Result Logout(){
        //TODO  删除token
        return null;
    }

    @PostMapping("logon")
    public Result Logon(@RequestBody Resuser resuser, HttpServletRequest request){
        try {
            Integer result = userService.logon(resuser, request);
            return  Result.success("添加成功");
        }catch (Exception e){
            return  Result.fail(e.getMessage());
        }
    }


}
