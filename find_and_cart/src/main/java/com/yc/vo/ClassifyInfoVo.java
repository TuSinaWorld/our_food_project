package com.yc.vo;


import com.s3.bean.ClassifyInfo;
import com.s3.bean.SubClassifyInfo;

import java.util.List;

public class ClassifyInfoVo{

    //菜谱大类id
    private Long id;

    //菜谱大类名称
    private String name;


    //菜谱小类信息集合
    List<SubClassifyInfo> subList;


    public ClassifyInfoVo() {
    }

    public ClassifyInfoVo(Long id, String name, List<SubClassifyInfo> subList) {
        this.id = id;
        this.name = name;
        this.subList = subList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubClassifyInfo> getSubList() {
        return subList;
    }

    public void setSubList(List<SubClassifyInfo> subList) {
        this.subList = subList;
    }
}
