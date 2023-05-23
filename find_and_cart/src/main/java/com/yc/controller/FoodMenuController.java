package com.yc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.s3.bean.FoodsMaterialInfo;
import com.s3.bean.FoodsMenuInfo;
import com.yc.mapper.FoodsMaterialInfoMapper;
import com.yc.mapper.FoodsMenuInfoMapper;
import com.yc.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 乐哥
 * @Date: 2023/5/23
 * @Time: 14:17
 * @Description:
 */
@RestController
@RequestMapping("/food")
public class FoodMenuController {
    @Resource
    FoodsMenuInfoMapper foodsMenuInfoMapper;

    @Resource
    FoodsMaterialInfoMapper foodsMaterialInfoMapper;
    @GetMapping ("/all")
    public Result all(String pages){
        Long l = new Long(pages);
        Page<FoodsMenuInfo> page = new Page<>(l,8);
        foodsMenuInfoMapper.selectPage(page,new QueryWrapper<FoodsMenuInfo>()
                .eq("level",2));
        List<FoodsMenuInfo> list = page.getRecords();
        long total=page.getTotal();
        if(list.size()>0){
            return new Result(1,"查询成功",list,total);
        }else{
            return new Result(0,"查询失败");
        }
    }
    @RequestMapping("/menuDetail")
    public Result menuDetail(String id){
        FoodsMenuInfo foodsMenuInfo = foodsMenuInfoMapper.selectById(id);
        if(foodsMenuInfo==null){
            return new Result(0,"查询失败");
        }
        return new Result(1,"查询成功",foodsMenuInfo);
    }
    @RequestMapping("/materialAll")
    public Result material(String pages){
        Long l = new Long(pages);
        Page<FoodsMaterialInfo> page = new Page<>(l,8);
        foodsMaterialInfoMapper.selectPage(page,new QueryWrapper<FoodsMaterialInfo>()
                .eq("level",1));
        List<FoodsMaterialInfo> list = page.getRecords();
        long total=page.getTotal();
        if(list.size()>0){
            return new Result(1,"查询成功",list,total);
        }else{
            return new Result(0,"查询失败");
        }
    }
}
