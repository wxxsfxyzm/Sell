package com.carlyu.dao;

import com.carlyu.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单明细表DAO
 */
@Repository
@Component
public interface OrderDetailDAO extends JpaRepository<OrderDetail, String> {
    /**
     * 根据订单编号 查询相关订单信息
     */
    List<OrderDetail> findByOrderId(String orderId);
}
