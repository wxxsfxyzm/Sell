package com.carlyu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品对象(包含商品类目)
 */
@Data
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品类目名字
     */
    @JsonProperty("name")
    private String categoryName;

    /**
     * 商品类目编号
     */
    @JsonProperty("type")
    private Integer categoryType;

    /**
     * ProductInfoVO
     */
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}