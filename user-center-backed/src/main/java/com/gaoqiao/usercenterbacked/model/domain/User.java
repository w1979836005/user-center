package com.gaoqiao.usercenterbacked.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    /**
     * 性别：0-女, 1-男
     */
    private Integer gender;

    /**
     * 密码（加密存储）
     */
    private String password;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 是否有效：0-封禁, 1-正常
     */
    private Integer isValid;

    /**
     * 是否删除：0-未删除, 1-已删除（逻辑删除）
     */
    @TableLogic  //逻辑删除标识
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}