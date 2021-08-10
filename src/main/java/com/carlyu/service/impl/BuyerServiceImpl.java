package com.carlyu.service.impl;

import com.carlyu.dto.OrderDTO;
import com.carlyu.enums.ResultEnum;
import com.carlyu.exception.SellException;
import com.carlyu.service.BuyerService;
import com.carlyu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    /**
     * 查询一个订单
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }


    /**
     * 取消订单
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        // 根据订单ID  查询单个订单
        OrderDTO orderDTO = orderService.findById(orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            return null;
        }
        return orderService.cancel(orderDTO);

    }

    /**
     * 判断订单的所属用户
     */
    private OrderDTO checkOrderOwner(String openid, String orderId) {
        // 根据订单ID 查询单个订单
        OrderDTO orderDTO = orderService.findById(orderId);
        if (orderDTO == null) {
            return null;
        }

        // 判断是否是自己的订单 抛出自定义异常: "该订单不属于当前用户"
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
