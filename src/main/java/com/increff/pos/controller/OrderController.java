package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.SalesReport;
import com.increff.pos.model.SalesReportForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Api
@RequestMapping(path="/orders")
@RestController
public class OrderController {

    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value="Get a Order Details")
    @RequestMapping(path="/{id}",method=RequestMethod.GET)
    public OrderData getOrderData(@PathVariable int id) throws ApiException
    {
        return orderDto.get(id);
    }

    @ApiOperation(value="Gives all Order Details")
    @RequestMapping(path="",method= RequestMethod.GET)
    public List<OrderData> getAllOrderDetails()throws ApiException
    {
        return orderDto.getAll();
    }

    @ApiOperation(value="Adds an Order Details")
    @RequestMapping(path="", method = RequestMethod.POST)
    public OrderData insertOrder()throws ApiException
    {
        return orderDto.add();
    }

    @ApiOperation(value="Set Order Status Placed")
    @RequestMapping(path="/{id}/place-order",method=RequestMethod.PUT)
    public Integer setOrderStatusPlaced(@PathVariable int id)throws ApiException
    {
        return orderDto.updateOrderStatusPlaced(id);
    }

    @ApiOperation(value="Get Order Invoice for Order Id")
    @RequestMapping(path="/{orderId}/invoices", method = RequestMethod.GET)
    public void getOrderInvoice(@PathVariable int orderId, HttpServletResponse response)throws ApiException, IOException, TransformerException
    {
        orderDto.getOrderInvoice(orderId, response);
    }

    @ApiOperation(value = "Get Sales report ")
    @RequestMapping(path = "/orders/sales-reports", method = RequestMethod.POST)
    public List<SalesReport> getsSalesReport(@RequestBody SalesReportForm salesReportForm) throws ApiException {
        return orderDto.getSalesReport(salesReportForm);
    }


}
