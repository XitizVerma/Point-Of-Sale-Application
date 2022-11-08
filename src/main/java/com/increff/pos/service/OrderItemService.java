package com.increff.pos.service;

import com.google.protobuf.Api;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Transactional(rollbackFor = ApiException.class)
@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public void add(OrderItemPojo orderItemPojo)throws ApiException
    {
      if(orderItemPojo.getQuantity()<=0)
        {
            throw new ApiException("Quantity must be greater than 0");
        }
        orderItemDao.add(orderItemPojo);
    }

    public List<OrderItemPojo> getAll()throws ApiException
    {
        return orderItemDao.selectAll();
    }

    public OrderItemPojo get(Integer orderId,String barcode)throws ApiException
    {
        return getCheckIdBarcode(orderId,barcode);

    }

//    public OrderItemPojo getCheck(Integer orderId,String barcode)throws ApiException
//    {
//        OrderItemPojo orderItemPojo=getCheckIdBarcode(orderId,barcode);
//        if(isNull(orderItemPojo))
//        {
//            throw new ApiException("OrderItem with given Id does Not exist, id : "+orderId + " barcode "+barcode);
//        }
//        return orderItemPojo;
//    }

    public void update(OrderItemPojo orderItemPojo)throws ApiException
    {

        OrderItemPojo orderItemPojo2=getCheckIdBarcode(orderItemPojo.getOrderId(), orderItemPojo.getBarcode());
        orderItemPojo2.setQuantity(orderItemPojo.getQuantity());
        orderItemPojo2.setSellingPrice(orderItemPojo.getSellingPrice());
        orderItemDao.update();//SYMBOLIC
    }

    public void delete(Integer orderId,String barcode)throws ApiException
    {
        getCheckIdBarcode(orderId,barcode);
        orderItemDao.delete(orderId,barcode);
    }

    public OrderItemPojo getCheckIdBarcode(Integer orderId,String barcode)throws ApiException
    {
        OrderItemPojo orderItemPojo = orderItemDao.selectFromOrderIdBarcode(orderId,barcode);
        if(isNull(orderItemPojo))
        {
            throw new ApiException("Order Item with barcode "+barcode+" is not present in the orderid "+orderId);
        }
        return orderItemPojo;
    }

    public List<OrderItemPojo> selectFromOrderId(Integer orderId)
    {
        return orderItemDao.selectFromOrderId(orderId);
    }

    public List<OrderItemPojo> selectFormOrderIdList(List<Integer>orderIdList)
    {
        return orderItemDao.selectFromOrderIdList(orderIdList);
    }

    public OrderItemPojo selectFromOrderIdBarcode(Integer orderId,String barcode)
    {
        return orderItemDao.selectFromOrderIdBarcode(orderId,barcode);
    }

}
