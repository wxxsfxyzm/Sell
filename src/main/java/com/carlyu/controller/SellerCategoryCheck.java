package com.carlyu.controller;

import com.carlyu.entity.ProductCategory;
import com.carlyu.service.ProductCategoryService;
import com.carlyu.util.ResultVOUtil;
import com.carlyu.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
//@EnableRedisHttpSession
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryCheck {
    @Autowired
    ProductCategoryService productCategoryService;

    @PostMapping("/newCategoryTypeDuplicateCheck")
    public ResultVO DuplicateCheck(
            @RequestParam(value = "categoryType") Integer categoryType) {
        String info = "校验是否重复";
        log.info(info);

        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        for (ProductCategory productCategory : productCategoryList) {
            if (Objects.equals(productCategory.getCategoryType(), categoryType)) {
                info = "查找到了重复项";
                log.warn(info);
                return ResultVOUtil.fail(-1, info);
            } else {
                info = "不重复";
                log.info(info);
            }
        }
        info = "都不重复，放行";
        log.info(info);
        return ResultVOUtil.success(0, info);
    }
}
