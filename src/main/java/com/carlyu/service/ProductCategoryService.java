package com.carlyu.service;

import com.carlyu.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 根据某个商品类目 ID 查询商品类目
     */
    ProductCategory findById(Integer categoryId);

    /**
     * 查询所有的商品类目
     */
    List<ProductCategory> findAll();

    /**
     * 根据商品类目 ID 列表 查询商品类目列表
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 新增商品类目
     */
    ProductCategory save(ProductCategory productCategory);
}
