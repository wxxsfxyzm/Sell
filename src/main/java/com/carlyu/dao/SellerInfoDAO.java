package com.carlyu.dao;

import com.carlyu.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 卖家信息DAO
 */
@Repository
public interface SellerInfoDAO extends JpaRepository<SellerInfo, String> {
    /**
     * 根据openid查询用户信息
     */
    SellerInfo findByOpenid(String openid);
}
