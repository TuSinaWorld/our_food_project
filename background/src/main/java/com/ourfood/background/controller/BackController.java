package com.ourfood.background.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourfood.background.bean.Result;
import com.ourfood.background.dao.*;
import com.s3.bean.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Resource
    UserInfoDao userInfoDao;
    @Resource
    ClassifyInfoDao classifyInfoDao;
    @Resource
    SubClassifyInfoDao subClassifyInfoDao;
    @Resource
    AdvertiserInfoDao advertiserInfoDao;


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
            adminInfos.forEach(adminInfo -> adminInfo.setPassword("***"));
            return Result.success("查询成功",adminInfos);
        }catch (Exception e){
            log.error("查询失败" + e.getMessage());
            return Result.failure("查询失败",null);
        }
    }

    @RequestMapping("/showUsersInfo.admin")
    public Result showUsersInfo(){
        List<UserInfo> userInfos = null;
        try{
            userInfos = userInfoDao.selectList(null);
            userInfos.forEach(userInfo -> userInfo.setPassword("***"));
            return Result.success("查询成功",userInfos);
        }catch (Exception e){
            log.error("查询失败" + e.getMessage());
            return Result.failure("查询失败",null);
        }
    }

    @RequestMapping("/banUser.admin")
    public Result banUser(@RequestParam String userId){
        try{
            int i = userInfoDao.deleteById(userId);
            return Result.success("封禁成功",i);
        }catch (Exception e){
            log.error("封禁失败" + e.getMessage());
            return Result.failure("封禁失败",null);
        }
    }

    @RequestMapping("/getClassifyInfo.admin")
    public Result getClassifyInfo(){
        List<ClassifyInfo> classifyInfos = null;
        try{
            classifyInfos = classifyInfoDao.selectList(null);
            return Result.success("查询成功",classifyInfos);
        }catch (Exception e){
            log.error("查询失败" + e.getMessage());
            return Result.failure("查询失败",null);
        }
    }

    @RequestMapping("/deleteClassifyInfo.admin")
    public Result deleteClassifyInfo(@RequestParam String id){
        try{
            int i = classifyInfoDao.deleteById(id);
            return Result.success("删除成功",i);
        }catch (Exception e){
            log.error("删除失败" + e.getMessage());
            return Result.failure("删除失败",null);
        }
    }

    @RequestMapping("/addClassifyInfo.admin")
    public Result addClassifyInfo(@RequestParam String name,@RequestParam("descroiption") String description){
        ClassifyInfo classifyInfo = new ClassifyInfo();
        classifyInfo.setName(name);
        classifyInfo.setDescroiption(description);
        try{
            int insert = classifyInfoDao.insert(classifyInfo);
            return Result.success("添加成功",insert);
        }catch (Exception e){
            log.error("添加失败" + e.getMessage());
            return Result.failure("添加失败",null);
        }
    }

    @RequestMapping("/updateClassifyInfo.admin")
    public Result updateClassifyInfo(@RequestParam Long id,@RequestParam String name,@RequestParam("descroiption") String description) {
        ClassifyInfo classifyInfo = new ClassifyInfo();
        classifyInfo.setId(id);
        classifyInfo.setName(name);
        classifyInfo.setDescroiption(description);
        try {
            int update = classifyInfoDao.updateById(classifyInfo);
            return Result.success("更新成功", update);
        } catch (Exception e) {
            log.error("更新失败" + e.getMessage());
            return Result.failure("更新失败", null);
        }
    }
    @RequestMapping("/getSubClassifyInfo.admin")
    public Result getSubClassifyInfo(){
        List<SubClassifyInfo> subClassifyInfos = null;
        try{
            subClassifyInfos = subClassifyInfoDao.selectList(null);
            return Result.success("查询成功",subClassifyInfos);
        }catch (Exception e){
            log.error("查询失败" + e.getMessage());
            return Result.failure("查询失败",null);
        }
    }

    @RequestMapping("/deleteSubClassifyInfo.admin")
    public Result deleteSubClassifyInfo(@RequestParam String id){
        try{
            int i = subClassifyInfoDao.deleteById(id);
            return Result.success("删除成功",i);
        }catch (Exception e){
            log.error("删除失败" + e.getMessage());
            return Result.failure("删除失败",null);
        }
    }

    @RequestMapping("/addSubClassifyInfo.admin")
    public Result addSubClassifyInfo(@RequestParam String name,String description,Long classifyId){
        SubClassifyInfo subClassifyInfo = new SubClassifyInfo();
        subClassifyInfo.setName(name);
        subClassifyInfo.setDescription(description);
        subClassifyInfo.setClassifyId(classifyId);
        try{
            int insert = subClassifyInfoDao.insert(subClassifyInfo);
            return Result.success("添加成功",insert);
        }catch (Exception e){
            log.error("添加失败" + e.getMessage());
            return Result.failure("添加失败",null);
        }
    }

    @RequestMapping("/updateSubClassifyInfo.admin")
    public Result updateSubClassifyInfo(@RequestParam Long id,@RequestParam String name,String description,Long classifyId) {
        SubClassifyInfo subClassifyInfo = new SubClassifyInfo();
        subClassifyInfo.setId(id);
        subClassifyInfo.setName(name);
        subClassifyInfo.setDescription(description);
        subClassifyInfo.setClassifyId(classifyId);
        try {
            int update = subClassifyInfoDao.updateById(subClassifyInfo);
            return Result.success("更新成功", update);
        } catch (Exception e) {
            log.error("更新失败" + e.getMessage());
            return Result.failure("更新失败", null);
        }
    }
    @RequestMapping("/getAdvertiserInfo.admin")
    public Result getAdvertiserInfo(){
        List<AdvertiserInfo> advertiserInfos = null;
        try{
            advertiserInfos = advertiserInfoDao.selectList(null);
            return Result.success("查询成功",advertiserInfos);
        }catch (Exception e){
            log.error("查询失败" + e.getMessage());
            return Result.failure("查询失败",null);
        }
    }

    @RequestMapping("/deleteAdvertiserInfo.admin")
    public Result deleteAdvertiserInfo(@RequestParam String id){
        try{
            int i = advertiserInfoDao.deleteById(id);
            return Result.success("删除成功",i);
        }catch (Exception e){
            log.error("删除失败" + e.getMessage());
            return Result.failure("删除失败",null);
        }
    }

    @RequestMapping("/addAdvertiserInfo.admin")
    public Result addAdvertiserInfo(@RequestParam String name,@RequestParam String content){
        AdvertiserInfo advertiserInfo = new AdvertiserInfo();
        advertiserInfo.setName(name);
        advertiserInfo.setContent(content);
        advertiserInfo.setTime(LocalDateTime.now());
        try{
            int insert = advertiserInfoDao.insert(advertiserInfo);
            return Result.success("添加成功",insert);
        }catch (Exception e){
            log.error("添加失败" + e.getMessage());
            return Result.failure("添加失败",null);
        }
    }





//    @RequestMapping("/getAdvertiserInfo.admin")
//    public Result getAdvertiserInfo(){
//        List<AdvertiserInfo> advertiserInfos = null;
//        try{
//            advertiserInfos = advertiserInfoDao.selectList(null);
//            return Result.success("查询成功",advertiserInfos);
//        }catch (Exception e){
//            log.error("查询失败" + e.getMessage());
//            return Result.failure("查询失败",null);
//        }
//    }
//
//    @RequestMapping("/deleteAdvertiserInfo.admin")
//    public Result deleteAdvertiserInfo(@RequestParam String id){
//        try{
//            int i = advertiserInfoDao.deleteById(id);
//            return Result.success("删除成功",i);
//        }catch (Exception e){
//            log.error("删除失败" + e.getMessage());
//            return Result.failure("删除失败",null);
//        }
//    }

}
