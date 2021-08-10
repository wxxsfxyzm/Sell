package com.carlyu.dto;

import lombok.Data;

/**
 * 购物车DTO  -- 购物车数据传输对象
 * Created by liyan on 2021/7/18.
 */
@Data
public class CartDTO {
    /**
     * 商品Id
     */
    private String productId;

    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
