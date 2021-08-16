package com.carlyu.service.impl;

import com.carlyu.dao.SellerInfoDAO;
import com.carlyu.entity.SellerInfo;
import com.carlyu.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卖家端用户信息Service实现类
 */
@Service
@Transactional
public class SellerInfoServiceImpl implements SellerInfoService {
    /**
     * 卖家信息DAO
     */
    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    /**
     * 通过openid查询卖家端信息
     */
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDAO.findByOpenid(openid);
    }

}

