package com.carlyu.service;

import com.carlyu.dto.OrderDTO;

/**
 * 买家Service
 */
public interface BuyerService {
    /**
     * 查询一个订单
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     */
    OrderDTO cancelOrder(String openid, String orderId);

}
