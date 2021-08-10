package com.carlyu.dao;

import com.carlyu.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 订单主表DAO
 */
@Repository
@Component
public interface OrderMasterDAO extends JpaRepository<OrderMaster, String> {
    /**
     * 根据微信端的openid查询订单主表信息
     */
    Page<OrderMaster> queryByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
