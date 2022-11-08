package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
public class SalesReport {

    private String brand;
    private String category;
    private Integer quantity;
    private Double revenue;

    //DEFAULT CONSTRUCTOR
    public SalesReport()
    {

    }

    //PARAM CONSTRUCTOR
    public SalesReport(String brand,String category,Integer quantity,Double revenue)
    {
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        this.brand=brand;
        this.category=category;
        this.quantity=quantity;
        this.revenue=revenue;
    }
}
