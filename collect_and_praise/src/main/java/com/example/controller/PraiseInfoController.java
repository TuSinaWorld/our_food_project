package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.Result;
import com.example.dao.PraiseInfoDao;
import com.s3.bean.CollectInfo;
import com.s3.bean.PraiseInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import util.JwtUtil;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */

@RestController
@RequestMapping("/praise")
public class PraiseInfoController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PraiseInfoDao praiseInfoDao;

    /**
     * 添加点赞记录
     * @param foodsId
     * @param name
     * @param token
     * @return
     */
    @RequestMapping("/insertPraise")
    public Result InsertPraise(@RequestParam Long foodsId,@RequestParam String name,@RequestHeader String token){
        //获取用户userid
        String userid;
        try{
            //读取token中唯一的内容 用户Id
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.get("user").toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        long userId = Long.parseLong(userid);

        //判断该菜谱是否被该用户点赞过
        QueryWrapper<PraiseInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name",name);
        queryWrapper.eq("foodsId",foodsId);
        queryWrapper.eq("userId",userId);
        PraiseInfo praiseInfo = praiseInfoDao.selectOne(queryWrapper);
        if (praiseInfo != null){
            praiseInfoDao.deleteById(praiseInfo.getId());
            return Result.success("取消点赞成功 ...",null);
        }

        //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);

        //添加点赞记录
        PraiseInfo praiseInfo1 = new PraiseInfo();
        praiseInfo1.setFoodsId(foodsId);
        praiseInfo1.setName(name);
        praiseInfo1.setUserId(userId);
        praiseInfo1.setTime(time);
        praiseInfoDao.insert(praiseInfo1);

        return Result.success("点赞添加成功 ...",null);
    }

    /**
     * 统计菜谱点赞数
     * @param
     * @return
     */
    @RequestMapping("/sumPraise")
    public Result SumPraise(String id){
        QueryWrapper<PraiseInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("foodsId",id);
        List<PraiseInfo> praiseInfos = praiseInfoDao.selectList(queryWrapper);
        int size = praiseInfos.size();
        return Result.success("点赞数 ...",size);
    }
}
