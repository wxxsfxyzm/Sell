package com.carlyu.service;

import com.carlyu.dto.CartDTO;
import com.carlyu.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品Service
 */
public interface ProductInfoService {
    /**
     * 根据商品 ID 查询商品信息
     */
    ProductInfo findById(String productId);

    /**
     * 查询所有上架商品信息列表
     *
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有商品信息列表
     *
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 新增商品
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);

}
