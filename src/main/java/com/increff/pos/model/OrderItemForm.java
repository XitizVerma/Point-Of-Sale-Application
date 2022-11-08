package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemForm {

    @NotNull(message = "OrderId cant be Null")
    private Integer orderId;

    @NotNull(message = "Barcode cant be Null")
    private String barcode;

    @Min(value=1, message = "Quantity must bwe greater than equal to 1")
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;

    @Min(value=0, message = "Selling Price cannot be less than 0")
    @NotNull(message = "Selling Price cannotbe empty")
    private Double sellingPrice;
}
