package com.s3.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 菜谱大类信息表
 * </p>
 *
 * @author yjl
 * @since 2023-05-23
 */
@TableName("classify_info")
public class ClassifyInfo implements Serializable {

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
    private String descroiption;


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

    public String getDescroiption() {
        return descroiption;
    }

    public void setDescroiption(String descroiption) {
        this.descroiption = descroiption;
    }

    @Override
    public String toString() {
        return "ClassifyInfo{" +
        "id=" + id +
        ", name=" + name +
        ", descroiption=" + descroiption +
        "}";
    }
}
