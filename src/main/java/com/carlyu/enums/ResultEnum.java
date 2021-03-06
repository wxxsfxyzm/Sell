package com.carlyu.enums;

import lombok.Getter;

/**
 * 结果枚举封装
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(100, "该商品不存在"),

    PRODUCT_STOCK_ERROR(101, "商品库存不正确"),

    ORDER_NOT_EXIST(102, "订单不存在"),

    ORDER_DETAIL_NOT_EXIST(103, "订单明细不存在"),

    ORDER_STATUS_ERROR(104, "订单状态不正确"),

    ORDER_UPDATE_FAIL(105, "订单更新失败"),

    ORDER_DETAIL_EMPTY(106, "订单明细为空"),

    ORDER_PAY_STATUS_ERROR(107, "订单支付状态不正确"),

    PARAM_ERROR(108, "参数不正确"),

    CART_EMPTY(109, "购物车为空"),

    ORDER_OWNER_ERROR(110, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(111, "微信公众账号方面错误"),

    ORDER_CANCEL_SUCCESS(112, "订单取消成功"),

    ORDER_FINISH_SUCCESS(113, "订单完结成功"),

    PRODUCT_STATUS_ERROR(114, "商品状态不正确"),

    LOGIN_FAIL(115, "登录失败, 登录信息不正确");

    private final Integer code;

    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
