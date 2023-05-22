package com.s3.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 笔记点赞信息表
 * </p>
 *
 * @author yc
 * @since 2023-05-19
 */
@TableName("praise_info")
public class PraiseInfo implements Serializable {

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
     * 时间
     */
    private LocalDateTime time;

    /**
     * 笔记id
     */
    private Long notesId;

    /**
     * 菜谱id
     */
    private Long foodsId;

    private Long userId;

    private Integer level;


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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getNotesId() {
        return notesId;
    }

    public void setNotesId(Long notesId) {
        this.notesId = notesId;
    }

    public Long getFoodsId() {
        return foodsId;
    }

    public void setFoodsId(Long foodsId) {
        this.foodsId = foodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "PraiseInfo{" +
        "id=" + id +
        ", name=" + name +
        ", time=" + time +
        ", notesId=" + notesId +
        ", foodsId=" + foodsId +
        ", userId=" + userId +
        ", level=" + level +
        "}";
    }
}
