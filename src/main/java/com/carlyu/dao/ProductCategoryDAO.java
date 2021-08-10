package com.carlyu.dao;

import com.carlyu.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品类目 DAO
 */
@Repository
@Component
public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Integer> {
    /**
     * 一次性 根据商品类目查询
     */
    List<ProductCategory> queryByCategoryTypeIn(List<Integer> categoryTypeList);
}
