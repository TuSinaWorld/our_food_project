package com.s3.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 笔记评论信息表
 * </p>
 *
 * @author yc
 * @since 2023-05-19
 */
@TableName("comment_notes_info")
public class CommentNotesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 评论人
     */
    private String name;

    /**
     * 关联的模块id
     */
    private Long foreignId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getForeignId() {
        return foreignId;
    }

    public void setForeignId(Long foreignId) {
        this.foreignId = foreignId;
    }

    @Override
    public String toString() {
        return "CommentNotesInfo{" +
        "id=" + id +
        ", content=" + content +
        ", time=" + time +
        ", name=" + name +
        ", foreignId=" + foreignId +
        "}";
    }
}
