package com.carlyu.service.impl;

import com.carlyu.dao.ProductInfoDAO;
import com.carlyu.dto.CartDTO;
import com.carlyu.entity.ProductInfo;
import com.carlyu.enums.ProductStatusEnum;
import com.carlyu.enums.ResultEnum;
import com.carlyu.exception.SellException;
import com.carlyu.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品Service实现类
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDAO productInfoDAO;

    /**
     * 根据商品 ID 查询商品信息
     */
    @Override
    public ProductInfo findById(String productId) {
        return productInfoDAO.findById(productId).orElse(null);
    }

    /**
     * 查询所有上架商品信息列表
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDAO.queryByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 分页查询所有商品信息列表
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDAO.findAll(pageable);
    }

    /**
     * 新增商品
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    /**
     * 加库存
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            // 根据商品ID查询 商品信息
            ProductInfo productInfo = productInfoDAO.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                // 抛出自定义异常 "该商品不存在"
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 增加库存
            Integer stocksNum = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stocksNum); // 设置库存
            productInfoDAO.save(productInfo);     // 更新库存
        }
    }


    /**
     * 减库存
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            // 根据商品ID查询商品信息
            ProductInfo productInfo = productInfoDAO.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST); //  抛出"该商品不存在"异常
            }
            // TODO 利用Redis的锁机制来解决：项目的优化部分
            // 剩余库存
            Integer remainStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (remainStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR); //  抛出"商品库存不正确"异常
            }
            productInfo.setProductStock(remainStock); // 重新设置库存
            productInfoDAO.save(productInfo);  // 保存商品信息
        }
    }

    /**
     * 商品上架
     */
    @Override
    public ProductInfo onSale(String productId) {
        //  根据商品ID查询 商品信息
        ProductInfo productInfo = productInfoDAO.findById(productId).orElse(null);

        // 如果商品不存在 则抛出 "该商品不存在" 异常
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        // 如果商品状态已经是上架(0)了 则抛出 "商品状态不正确" 异常
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        // 更新商品的状态 将 下架(1)商品的状态 改成  商品上架(0)
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoDAO.save(productInfo);  // 更新商品状态 为 上架0
    }

    /**
     * 商品下架
     */
    @Override
    public ProductInfo offSale(String productId) {
        //  根据商品ID查询 商品信息
        ProductInfo productInfo = productInfoDAO.findById(productId).orElse(null);

        // 如果商品不存在 则抛出 "该商品不存在" 异常
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        // 如果商品状态已经是下架(1)了 则抛出 "商品状态不正确" 异常
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        // 更新商品的状态 将 上架(0)商品的状态 改成  商品下架(1)
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoDAO.save(productInfo);  // 更新商品状态 为 下架1
    }

}
