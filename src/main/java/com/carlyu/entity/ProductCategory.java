package com.carlyu.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品类目表 对应的 JavaBean
 */
// 当 JavaBean 的名字和对应的表名不一致的时候用@Table(name="xxx")做映射
// @Table(name = "product_category")
@Data
@Entity
@Component
@DynamicUpdate
public class ProductCategory implements Serializable {
    /**
     * 序列化 ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 类目 id
     */
    @Id
    @GeneratedValue
    private Integer categoryId;
    /**
     * 类目名
     */
    private String categoryName;
    /**
     * 类目编号
     */
    private Integer categoryType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}