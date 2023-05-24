package com.yc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.s3.bean.ClassifyInfo;
import com.s3.bean.FoodsMaterialInfo;
import com.s3.bean.FoodsMenuInfo;
import com.s3.bean.SubClassifyInfo;
import com.yc.mapper.ClassifyInfoMapper;
import com.yc.mapper.FoodsMaterialInfoMapper;
import com.yc.mapper.FoodsMenuInfoMapper;
import com.yc.mapper.SubClassifyInfoMapper;
import com.yc.util.Result;
import com.yc.vo.ClassifyInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    ClassifyInfoMapper classifyInfoMapper;

    @Resource
    SubClassifyInfoMapper subClassifyInfoMapper;
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
    @RequestMapping("/materialDetail")
    public Result materialDetail(String id){
        FoodsMaterialInfo foodsMaterialInfo = foodsMaterialInfoMapper.selectById(id);
        if(foodsMaterialInfo==null){
            return new Result(0,"查询失败");
        }
        return new Result(1,"查询成功",foodsMaterialInfo);
    }

    @RequestMapping("/menuPage")
    public Result menuPage(String subid,String page){
        if(page==null && page==""){
            page="1";
        }
        Long l = new Long(page);
        Page<FoodsMenuInfo> pages = new Page<>(l,5);
        foodsMenuInfoMapper.selectPage(pages,new QueryWrapper<FoodsMenuInfo>()
                .eq("subId",subid));
        List<FoodsMenuInfo> menuInfos = pages.getRecords();
        long p = pages.getTotal();//总记录数
        long t = pages.getPages();//总页数
        if(menuInfos==null){
            return new Result(0,"查询失败");
        }
        return new Result(1,"查询成功",menuInfos,p,t);
    }
    @RequestMapping("/allClassify")
    public Result allClassify(){
        List<ClassifyInfo> classifyInfo= classifyInfoMapper.selectList(null);
        if(classifyInfo.size()<=0 || classifyInfo==null){
            return new Result(0,"查询失败");
        }
        List<ClassifyInfoVo> info= new ArrayList<ClassifyInfoVo>();
        for(ClassifyInfo classify :classifyInfo){
            ClassifyInfoVo classifyInfoVo = new ClassifyInfoVo();
            classifyInfoVo.setName(classify.getName());
            classifyInfoVo.setId(classify.getId());
            classifyInfoVo.setSubList(subClassifyInfoMapper.selectList(new QueryWrapper<SubClassifyInfo>()
                    .eq("classifyId",classify.getId())));
            info.add(classifyInfoVo);
        }
        System.out.println(info);
        return new Result(1,"查询成功",info);
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
