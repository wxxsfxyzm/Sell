package com.carlyu.service;

import com.carlyu.entity.SellerInfo;

/**
 * 卖家端用户信息Service
 */
public interface SellerInfoService {
    /**
     * 通过openid查询卖家端信息
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
