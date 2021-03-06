package com.carlyu.entity;

import com.carlyu.enums.ProductStatusEnum;
import com.carlyu.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表 对应的 JavaBean
 * Created by liyan on 2021/7/17.
 */
@Entity
@Data
@Component
@DynamicUpdate
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String productId; // 注意商品 ID 没有主键自增
    /**
     * 名字.
     */
    private String productName;
    /**
     * 单价.
     */
    private BigDecimal productPrice;
    /**
     * 库存.
     */
    private Integer productStock;
    /**
     * 描述.
     */
    private String productDescription;
    /**
     * 小图.
     */
    private String productIcon;
    /**
     * 状态, 0-正常 1-下架.
     * 魔鬼数字  =>
     * （1）常量接口
     * （2）枚举
     */
    //（1）常量接口
    // private Integer productStatus = Constant.PRODUCT_STATUS_UP;
    //（2）枚举
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /**
     * 类目编号.
     */
    private Integer categoryType;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date updateTime;

    /**
     * 获取产品的枚举类: 商品的各个状态都在里面
     */
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}