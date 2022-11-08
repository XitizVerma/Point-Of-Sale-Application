package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemUpdateForm {

    @NotNull(message="orderId cant be Null")
    private Integer orderId;

    @NotNull(message = "Barcode cant be Null")
    private String barcode;

    @Min(value = 1,message = "Quantity must be greater than 1")
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;

    @Min(value=0,message = "Selling Price must be greater than 0")
    private Double sellingPrice;

}
