package com.carlyu.dao;

import com.carlyu.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品DAO
 */
@Repository
public interface ProductInfoDAO extends JpaRepository<ProductInfo, String> {
    /**
     * 通过商品的状态来查上架的商品
     */
    List<ProductInfo> queryByProductStatus(Integer productStatus);
}
