package com.carlyu.controller;

import com.carlyu.converter.OrderForm2OrderDTOConverter;
import com.carlyu.dto.OrderDTO;
import com.carlyu.enums.ResultEnum;
import com.carlyu.exception.SellException;
import com.carlyu.form.OrderForm;
import com.carlyu.service.BuyerService;
import com.carlyu.service.OrderService;
import com.carlyu.util.ResultVOUtil;
import com.carlyu.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // TODO (1) 创建订单 /sell/buyer/order/create
    // BindingResult 用在实体类校验信息返回结果绑定
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        //  实体类校验信息返回结果 错误处理
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 将OrderForm转换成OrderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        // 判断订单详情列表是否为空
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO); // 创建订单
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", createResult.getOrderId()); // 设置orderId
        return ResultVOUtil.success(map);
    }

    // (2) 订单列表    GET /sell/buyer/order/list
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(
            @RequestParam("openid") String openid,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        // 自Spring 5.3版本起, isEmpty(Object)已建议弃用, 使用hasLength(String)或hasText(String)替代
        // if (StringUtils.isEmpty(openid))
        if (!StringUtils.hasText(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        // 查询订单列表
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
        // return ResultVOUtil.success(); // return null;

        // ResultVO resultVO = new ResultVO();
        // resultVO.setCode(0);
        // return resultVO;

    }

    // TODO (3) 订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId) {

        // OrderDTO orderDTO = orderService.findById(orderId);
        // 不安全的做法, 改进如下

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    // TODO (4) 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId) {

        //OrderDTO orderDTO = orderService.findById(orderId);
        // 不安全的做法, 改进如下
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);

        orderService.cancel(orderDTO);  // 取消订单
        return ResultVOUtil.success();
    }

}
