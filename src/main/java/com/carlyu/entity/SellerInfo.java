package com.carlyu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 卖家信息表 对应的 JavaBean
 */
@Data
@Entity
public class SellerInfo implements Serializable {
    /**
     * 卖家信息表 主键
     */
    @Id
    private String sellerId;

    /**
     * 用户名(这里没有用到 -- 为了以后的扩展)
     */
    private String username;

    /**
     * 密码(这里没有用到 -- 为了以后的扩展)
     */
    private String password;

    /**
     * 扫码登录 就用openid
     */
    private String openid;
}

