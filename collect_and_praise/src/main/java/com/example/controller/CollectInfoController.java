package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.Result;
import com.example.dao.CollectInfoDao;
import com.example.dao.PraiseInfoDao;
import com.s3.bean.CollectInfo;
import com.s3.bean.PraiseInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.JwtUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */

@RestController
@RequestMapping("/collect")
public class CollectInfoController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CollectInfoDao collectInfoDao;

    /**
     * 添加收藏记录
     * @param foodsId
     * @param name
     * @param token
     * @return
     */
    @RequestMapping("/inserCollect")
    public Result InsertCollect(@RequestParam Long foodsId,@RequestParam String name,@RequestHeader String token){
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
        QueryWrapper<CollectInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name",name);
        queryWrapper.eq("foodsId",foodsId);
        queryWrapper.eq("userId",userId);
        CollectInfo collectInfo = collectInfoDao.selectOne(queryWrapper);
        if (collectInfo != null){
            collectInfoDao.deleteById(collectInfo.getId());
            return Result.success("取消收藏成功 ...",null);
        }

        //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);

        //添加点赞记录
        CollectInfo collectInfo1 = new CollectInfo();
        collectInfo1.setFoodsId(foodsId);
        collectInfo1.setName(name);
        collectInfo1.setUserId(userId);
        collectInfo1.setTime(time);
        collectInfoDao.insert(collectInfo1);

        return Result.success("收藏添加成功 ...",collectInfo1);
    }

    /**
     * 统计菜谱收藏数
     * @param id
     * @return
     */
    @RequestMapping("/sumCollect")
    public Result SumCollect(String id){
        QueryWrapper<CollectInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("foodsId",id);
        List<CollectInfo> collectInfos =collectInfoDao.selectList(queryWrapper);
        int size = collectInfos.size();
        return Result.success("收藏数 ...",size);
    }
}
