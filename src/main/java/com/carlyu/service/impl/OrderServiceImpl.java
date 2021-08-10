package com.carlyu.service.impl;

import com.carlyu.converter.OrderMaster2OrderDTOConverter;
import com.carlyu.dao.OrderDetailDAO;
import com.carlyu.dao.OrderMasterDAO;
import com.carlyu.dto.CartDTO;
import com.carlyu.dto.OrderDTO;
import com.carlyu.entity.OrderDetail;
import com.carlyu.entity.OrderMaster;
import com.carlyu.entity.ProductInfo;
import com.carlyu.enums.OrderStatusEnum;
import com.carlyu.enums.PayStatusEnum;
import com.carlyu.enums.ResultEnum;
import com.carlyu.exception.SellException;
import com.carlyu.service.OrderService;
import com.carlyu.service.ProductInfoService;
import com.carlyu.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService; // 商品Serivce

    @Autowired
    private OrderDetailDAO orderDetailDAO;  // 商品明细DAO

    @Autowired
    private OrderMasterDAO orderMasterDAO;  // 订单主表DAO

    /**
     * 创建订单
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey(); // 随机生成orderId

        BigDecimal orderTotalPrice = new BigDecimal(BigInteger.ZERO);  // 订单总价

        // List<CartDTO> cartDTOList = new ArrayList<CartDTO>(); // 购物车列表

        // 1. 查询商品(数量、价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                // 抛出商品不存在异常
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 2. 计算订单总价
            // 一条订单明细的价格 = 单个商品的价钱 * 一条订单明细买的某个商品的个数
            // productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()));
            // 订单总价
            orderTotalPrice = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderTotalPrice);

            // 订单明细入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailDAO.save(orderDetail);
        }

        // CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
        // cartDTOList.add(cartDTO);


        // 3. 将订单信息写入数据库(OrderMaster和OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);  // 设置订单编号

        // 将orderDTO对象中的内容复制到orderMaster对象
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderTotalPrice); // 设置订单总价
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDAO.save(orderMaster);

        // 4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                        new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());

        productInfoService.decreaseStock(cartDTOList);

        BeanUtils.copyProperties(orderMaster, orderDTO);

        return orderDTO;

    }


    /**
     * 查询单个订单
     */
    @Override
    public OrderDTO findById(String orderId) {
        // 根据订单ID查询 订单信息
        OrderMaster orderMaster = orderMasterDAO.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 根据订单ID 查询  订单明细信息列表
        List<OrderDetail> orderDetailList = orderDetailDAO.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }


    /**
     * 查询订单列表
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        // 根据微信端的openid查询订单主表信息
        Page<OrderMaster> orderMasterPage = orderMasterDAO.queryByBuyerOpenid(buyerOpenid, pageable);

        // 用转换器 将OrderMaster转换成OrderDTO
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        ;
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());


    }

    /**
     * 取消订单
     */
    @Override
    // @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // TODO (1)判断订单状态
        // 判断如果不是新订单则不能取消订单(抛出自定义异常"不是新订单不能取消")
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单] 订单状态不正确(不是新订单不能取消), orderId={} orderStatus={}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            // 抛出自定义异常 "不是新订单不能取消"
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // TODO (2)修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateOrderMaster = orderMasterDAO.save(orderMaster); // 修改订单状态功能
        if (updateOrderMaster == null) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            // 抛出自定义异常 "订单更新失败"
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // TODO (3)返回库存
        // 根据订单ID 查询订单详情
        List<OrderDetail> orderDetailList =
                orderDetailDAO.findByOrderId(orderDTO.getOrderId());
        orderDTO.setOrderDetailList(orderDetailList);
        // 如果订单明细列表为空则抛出自定义异常 "订单明细为空"
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            // 抛出自定义异常 "订单明细为空"
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        // 返回购物车列表
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);  // 减库存


        // TODO (4)如果已支付, 需要给用户退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return orderDTO;
    }

    /**
     * 完结订单
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        // TODO (1)判断订单状态
        // 如果不是新订单
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            // 不是新订单不能取消
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // TODO (2)修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode()); // 将订单状态改成完结
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDAO.save(orderMaster);  // 修改订单状态为完结
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            // 订单更新失败
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;


    }

    /**
     * 支付订单
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        // TODO (1) 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            // 抛出自定义异常: "订单状态不正确"
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // TODO (2) 判断支付状态
        // 如果 不是 等待支付状态 则抛出自定义异常 订单支付状态不正确
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // TODO (3) 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode()); // 修改订单的支付状态字段
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDAO.save(orderMaster); // 修改支付状态
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);

            // 抛出自定义异常: "订单更新失败"
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;


    }
}
