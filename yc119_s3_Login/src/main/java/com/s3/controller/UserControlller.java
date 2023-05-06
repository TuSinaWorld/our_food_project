package com.s3.controller;


import com.s3.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControlller {
    @RequestMapping ("login")
    public Result Login(){
        return new Result(0,"iaiwh","ygaw");
    }


}
