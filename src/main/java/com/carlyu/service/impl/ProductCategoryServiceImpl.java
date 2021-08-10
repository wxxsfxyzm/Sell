package com.carlyu.service.impl;

import com.carlyu.dao.ProductCategoryDAO;
import com.carlyu.entity.ProductCategory;
import com.carlyu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类目 Service 实现类
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    /**
     * 根据某个商品类目 ID 查询商品类目
     */
    @Override
    public ProductCategory findById(Integer categoryId) {
        return productCategoryDAO.findById(categoryId).orElse(null);
    }

    /**
     * 查询所有的商品类目
     */
    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDAO.findAll();
    }

    /**
     * 根据商品类目 ID 列表 查询商品类目列表
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDAO.queryByCategoryTypeIn(categoryTypeList);
    }

    /**
     * 新增商品类目
     */
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryDAO.save(productCategory);
    }
}
