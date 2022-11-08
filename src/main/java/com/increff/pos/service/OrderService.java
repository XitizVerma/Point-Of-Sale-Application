package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional(rollbackFor = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public OrderPojo add() throws ApiException {

        OrderPojo orderPojo = new OrderPojo();
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        orderPojo.setTime(date);
        orderDao.add(orderPojo);
        return orderPojo;

    }

    public OrderPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    public List<OrderPojo> getAll() throws ApiException {

        return orderDao.selectAll();
    }

    public OrderPojo getCheck(Integer id) throws ApiException {
        OrderPojo orderPojo = orderDao.select(id);
        if (isNull(orderPojo)) {
            throw new ApiException("Order with given id does not exist, id : " + id);
        }
        return orderPojo;
    }

    public void updateOrderStatusPlaced(Integer id) throws ApiException
    {
        OrderPojo orderPojo=getCheck(id);
        orderPojo.setOrderPlaced(true);
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        orderPojo.setTime(date);
    }

    public List<OrderPojo> selectByFromDate(ZonedDateTime from,ZonedDateTime to)
    {
        return orderDao.selectByFromDate(from,to);
    }

}
