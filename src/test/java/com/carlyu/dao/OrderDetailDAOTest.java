package com.carlyu.dao;

import com.carlyu.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailDAOTest {
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    /**
     * 新增订单明细
     */
    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567811");
        orderDetail.setOrderId("1956782162833543926");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductId("11111113");
        orderDetail.setProductName("原味奶茶");
        orderDetail.setProductPrice(new BigDecimal(18));
        orderDetail.setProductQuantity(3);

        OrderDetail result = orderDetailDAO.save(orderDetail);
        Assert.assertNotNull(result);

    }

    /**
     * 根据订单编号 查询相关订单信息
     */
    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = orderDetailDAO.findByOrderId("1956782162833543926");
        Assert.assertNotEquals(0, orderDetailList.size());
    }
}