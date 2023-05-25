package com.example.controller;


import com.example.bean.Result;
import com.example.dao.AdvertiserDao;
import com.s3.bean.AdvertiserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 公告管理类
 */
@RestController
@RequestMapping("/advertiser")
public class AdvertiserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AdvertiserDao advertiserDao;

    /**
     * 获取最新(10条)公告信息
     * @return
     */
    @RequestMapping("/selectAadvertiser")
    public Result SelectAadvertiser(){
        List<AdvertiserInfo> advertiserInfoList = advertiserDao.selectList(null);
        if (advertiserInfoList.size() <= 10){
            return Result.success("最新公告内容",advertiserInfoList);
        }
        advertiserInfoList.subList(0,advertiserInfoList.size()-10).clear();
        return Result.success("最新十条公告内容",advertiserInfoList);
    }

}
