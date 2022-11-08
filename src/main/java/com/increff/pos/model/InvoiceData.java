package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "invoiceData")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceData {

    @XmlElement(name = "orderItems")
    private List<OrderItemData> orderItemDataList;


    private String orderTime;
    private Double total;
    private Integer orderId;

    private String invoiceTime;

    //DEFAULT CONSTRUCTOR
    public InvoiceData()
    {

    }

    //PARAM CONSTRUCTOR
    public InvoiceData(List<OrderItemData> orderItemDataList2, ZonedDateTime orderTime, Double total, Integer orderId)
    {
        this.orderItemDataList = new ArrayList<OrderItemData>();
        this.orderTime = orderTime.toLocalDate().toString()+" "+ orderTime.toLocalTime().toString();
        this.orderId=orderId;
        this.total=total;
        this.invoiceTime=ZonedDateTime.now().toString();
        for(OrderItemData orderItemData : orderItemDataList2)
        {
            this.orderItemDataList.add(orderItemData);
        }

    }

    public List<OrderItemData> getOrderItem()
    {
        return orderItemDataList;
    }

}
