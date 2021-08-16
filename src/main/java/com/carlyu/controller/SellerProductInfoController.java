package com.carlyu.controller;

import com.carlyu.entity.ProductCategory;
import com.carlyu.entity.ProductInfo;
import com.carlyu.exception.SellException;
import com.carlyu.form.ProductForm;
import com.carlyu.service.ProductCategoryService;
import com.carlyu.service.ProductInfoService;
import com.carlyu.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品Controller
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductInfoController {
    /**
     * 商品Service
     */
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 买家端 -- 商品列表
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);

        // 调用分页查询商品列表方法
        Page<ProductInfo> productInfoPageList = productInfoService.findAll(request);
        map.put("productInfoPageList", productInfoPageList); // 设置商品分页列表
        map.put("currentPage", page);  // 设置当前页
        map.put("pageSize", size);     // 设置每页显示多少条数据

        return new ModelAndView("product/list", map);
    }

    /**
     * 商品上架  http://127.0.0.1:8080/sell/seller/product/off_sale?productId=1
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(
            @RequestParam("productId") String productId,
            Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);  // 商品上架
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/on_sale_error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/on_sale_success", map);
    }

    /**
     * 商品下架 http://127.0.0.1:8080/sell/seller/product/on_sale?productId=1
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(
            @RequestParam("productId") String productId,
            Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);  // 商品下架
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/off_sale_error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/off_sale_success", map);
    }

    /**
     * 展示卖家商品-新增和修改页面
     * 商品新增和 商品修改 公共一个页面
     * (区别 展示商品修改页面时要有商品ID)
     */
    @GetMapping("/index")
    public ModelAndView index(
            @RequestParam(value = "productId", required = false) String productId,
            Map map) {
        // if (StringUtils.isEmpty())
        if (!StringUtils.hasText(productId)) // 如果商品ID为空则结束整个方法
        {
            // TODO 新增空商品
            // return null;
            ProductInfo productInfo = new ProductInfo();
            map.put("product", productInfo);

            //List<ProductCategory> productCategoryList = null;
            //map.put("categoryList", productCategoryList);
            //return new ModelAndView("product/index", map);
        } else {
            // 根据商品ID 查询某个商品的商品信息
            ProductInfo productInfo = productInfoService.findById(productId);
            map.put("product", productInfo);  // 设置产品信息
        }
        // 查询所有的商品类目
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("categoryList", productCategoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 商品新增/修改  功能
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (bindingResult.hasErrors()) {
            session.setAttribute("msg", bindingResult.getFieldError().getDefaultMessage());
            session.setAttribute("url", request.getContextPath() + "/seller/product/index");
            return new ModelAndView("common/error");
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            // 如果productId有值 说明是商品修改
            // if (!StringUtils.isEmpty(form.getProductId()))
            if (StringUtils.hasText(form.getProductId())) {
                productInfo = productInfoService.findById(form.getProductId());
            } else {
                // 如果productId为空, 说明是商品新增
                form.setProductId(KeyUtil.genUniqueKey());
            }

            // 将form对象中的数据 copy给 productInfo
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);   // 保存/更新 商品信息
        } catch (SellException e) {
            session.setAttribute("msg", e.getMessage());
            session.setAttribute("url", request.getContextPath() + "/seller/product/index");
            return new ModelAndView("common/product_save_error");
        }
        session.setAttribute("url", request.getContextPath() + "/seller/product/list");
        return new ModelAndView("common/product_save_success");
    }

}

