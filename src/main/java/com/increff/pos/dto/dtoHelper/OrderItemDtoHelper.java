package com.increff.pos.dto.dtoHelper;

import com.increff.pos.model.DataUI.OrderItemDataUI;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.OrderItemUpdateForm;
import com.increff.pos.pojo.OrderItemPojo;

public class OrderItemDtoHelper {

    public static OrderItemData convertOrderItemPojotoOrderItemData(OrderItemPojo orderItemPojo)
    {
        OrderItemData orderItemData=new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setBarcode(orderItemPojo.getBarcode());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderItemData;
    }

    public static OrderItemPojo convertOrderItemFormToOrderItemPojo(OrderItemForm orderItemForm)
    {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setBarcode(orderItemForm.getBarcode());
        orderItemPojo.setOrderId(orderItemForm.getOrderId());
        orderItemPojo.setSellingPrice(orderItemForm.getSellingPrice());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        return orderItemPojo;
    }

    public static OrderItemPojo convertOrderItemFormToOrderItemPojo(OrderItemUpdateForm orderItemUpdateForm)
    {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setQuantity(orderItemUpdateForm.getQuantity());
        orderItemPojo.setBarcode(orderItemUpdateForm.getBarcode());
        orderItemPojo.setSellingPrice(orderItemUpdateForm.getSellingPrice());
        orderItemPojo.setOrderId(orderItemUpdateForm.getOrderId());
        return orderItemPojo;
    }

    public static OrderItemData convertOrderItemPojoToOrderItemData(OrderItemPojo orderItemPojo)
    {
        OrderItemData orderItemData=new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        orderItemData.setBarcode(orderItemPojo.getBarcode());
        return orderItemData;
    }

    public static OrderItemDataUI convertOrderItemFormtoOrderItemDataUI(OrderItemForm orderItemForm)
    {
        OrderItemDataUI orderItemDataUI = new OrderItemDataUI();
        orderItemDataUI.setBarcode(orderItemForm.getBarcode());
        orderItemDataUI.setOrderId(orderItemForm.getOrderId());
        orderItemDataUI.setQuantity(orderItemForm.getQuantity());
        orderItemDataUI.setSellingPrice(orderItemForm.getSellingPrice());
        return orderItemDataUI;
    }


}
