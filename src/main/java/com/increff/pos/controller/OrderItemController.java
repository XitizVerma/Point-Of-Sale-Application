package com.increff.pos.controller;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.DataUI.OrderItemDataUI;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.OrderItemUpdateForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Api
@RestController
public class OrderItemController {

    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value="Adds an Order Item")
    @RequestMapping(path="/orders/order-items", method = RequestMethod.POST)
    public OrderItemDataUI insertOrderItem(@RequestBody OrderItemForm orderItemForm)throws ApiException
    {
        return orderItemDto.add(orderItemForm);
    }

    @ApiOperation(value="Gives all OrderItem Data")
    @RequestMapping(path="/orders/order-items", method = RequestMethod.GET)
    public List<OrderItemData> getAllProductDetails()throws ApiException
    {
        return orderItemDto.getAll();
    }

    @ApiOperation(value="Gives all Order Item Data for an Order")
    @RequestMapping(path="/orders/{orderId}/order-items",method=RequestMethod.GET)
    public List<OrderItemData> getOrderItemsForOrderId(@PathVariable Integer orderId)throws ApiException
    {
        return orderItemDto.getOrderItemForOrder(orderId);
    }

    @ApiOperation(value="Update an OrderItem")
    @RequestMapping(path = "/orders/order-items/{orderId}",method = RequestMethod.PUT)
    public OrderItemUpdateForm updateorderItem(@RequestBody OrderItemUpdateForm orderItemUpdateForm)throws ApiException
    {
        return orderItemDto.update(orderItemUpdateForm);
    }

    @ApiOperation(value="Delete OrderItem Data")
    @RequestMapping(path = "/orders/order-items/{orderId}/barcode/{barcode}", method=RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable int orderId, @PathVariable String barcode)throws ApiException
    {
        orderItemDto.delete(orderId,barcode);
    }

}