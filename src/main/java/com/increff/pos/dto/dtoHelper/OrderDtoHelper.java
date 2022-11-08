package com.increff.pos.dto.dtoHelper;

import com.increff.pos.model.OrderData;
import com.increff.pos.pojo.OrderPojo;

public class OrderDtoHelper {

    public static OrderData convertOrderPojotoOrderData(OrderPojo orderPojo)
    {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setTime(orderPojo.getTime().toString());
        orderData.setOrderPlaced(orderPojo.getOrderPlaced());
        return orderData;
    }
}
