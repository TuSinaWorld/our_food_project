package com.s3.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 菜谱信息表
 * </p>
 *
 * @author yc
 * @since 2023-05-19
 */
@TableName("foods_menu_info")
public class FoodsMenuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 菜谱标签
     */
    private String tips;

    /**
     * 菜谱小类id
     */
    private Long subId;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 上传人
     */
    private String userName;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 用户id
     */
    private Long uploadUserId;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(Long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    @Override
    public String toString() {
        return "FoodsMenuInfo{" +
        "id=" + id +
        ", name=" + name +
        ", description=" + description +
        ", tips=" + tips +
        ", subId=" + subId +
        ", fileId=" + fileId +
        ", fileName=" + fileName +
        ", userName=" + userName +
        ", level=" + level +
        ", uploadUserId=" + uploadUserId +
        "}";
    }
}
