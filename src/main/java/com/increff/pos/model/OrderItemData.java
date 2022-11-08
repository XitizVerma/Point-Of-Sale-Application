package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemData {

    @NotNull(message = "Id cant be Null")
    private Integer id;

    @NotNull(message = "OrderId cant be Null")
    private Integer orderId;

    @NotNull(message = "Barcode cant be Null")
    private String barcode;

    @Min(value = 1, message = "quantity must be greater than 1")
    @NotNull(message = "quantity cannot be Null")
    private Integer quantity;

    @Min(value = 0,message = "sellingPrice must be greater than 0")
    @NotNull(message = "Selling Price cant be Null")
    private Double sellingPrice;
}
