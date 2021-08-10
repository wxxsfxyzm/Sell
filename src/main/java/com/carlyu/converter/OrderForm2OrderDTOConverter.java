package com.carlyu.converter;

import com.carlyu.dto.OrderDTO;
import com.carlyu.entity.OrderDetail;
import com.carlyu.enums.ResultEnum;
import com.carlyu.exception.SellException;
import com.carlyu.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    /**
     * 将OrderForm转换成OrderDTO
     */
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        try {
            // 将OrderForm转换成OrderDTO
            // gson.fromJson(String json, Type typeOfT);
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            // 抛出自定义异常: "参数不正确"
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
