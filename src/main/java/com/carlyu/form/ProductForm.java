package com.carlyu.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 封装商品修改、商品新增 传递的数据
 */
@Data
public class ProductForm {

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名字
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品小图
     */
    private String productIcon;

    /**
     * 商品类目编号
     */
    private Integer categoryType;
}
