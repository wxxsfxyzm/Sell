package com.carlyu.controller;

import com.carlyu.entity.ProductCategory;
import com.carlyu.entity.ProductInfo;
import com.carlyu.service.ProductCategoryService;
import com.carlyu.service.ProductInfoService;
import com.carlyu.util.ResultVOUtil;
import com.carlyu.vo.ProductInfoVO;
import com.carlyu.vo.ProductVO;
import com.carlyu.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController // 返回 JSON 数据
@RequestMapping("/buyer/product")
public class BuyerProductInfoController {
    // 商品 Service
    @Autowired
    private ProductInfoService productInfoService;

    // 商品类目 Service
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询商品列表方法
     */
    @GetMapping("/list")
    public ResultVO List() {
        // TODO (1)查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        // TODO (2)查询商品类目列表 (1)和(2) 一次性查询
        /*List<Integer> categoryTypeList = new ArrayList<Integer>();
        for (ProductInfo productInfo : productInfoList){ // 从 查询到所有的上架商品中取出所有的 商品类目编号 productInfo.getCategoryType() 设置到 查询商品类目列表中
        categoryTypeList.add(productInfo.getCategoryType());
        } */
        // 查询不要放在循环里
        // 查询商品类目列表
        List<Integer> categoryTypeList = // 查询商品类目列表
                productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList =
                productCategoryService.findByCategoryTypeIn(categoryTypeList);
        // TODO (3)拼接数据
        // 创建一个空的商品列表
        List<ProductVO> productVOList = new ArrayList<ProductVO>(); // 遍历商品类目列表
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            // 创建一个空的商品详情列表
            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            // 遍历商品列表
            for (ProductInfo productInfo2 : productInfoList) {
                // 判断商品类目是否相等
                if (productInfo2.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    /*productInfoVO.setProductId(productInfo2.getProductId());
                    productInfoVO.setProductName(productInfo2.getProductName());
                    productInfoVO.setProductPrice(productInfo2.getProductPrice());
                    productInfoVO.setProductDescription(productInfo2.getProductDescription());
                    productInfoVO.setProductIcon(productInfo2.getProductIcon()); */
                    // 以上几个 set 合并为 以下这个方法 BeanUtils.copyProperties(src, dest);
                    // TODO 将商品详情对象 放置到 商品详情列表中
                    BeanUtils.copyProperties(productInfo2, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            } // end of 内层 for 循环
            productVO.setProductInfoVOList(productInfoVOList); // 设置商品详情列表
            productVOList.add(productVO);
        } // end of 外层 for 循环
        /*ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        // resultVO.setData(productVO);
        // 将 ProductVO 设置到 data 中 --- ProductVO 是商品对象(包含商品类目)
        resultVO.setData(productVOList);
        return resultVO; */
        return ResultVOUtil.success(productVOList);
    }


}
