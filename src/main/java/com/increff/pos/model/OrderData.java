package com.increff.pos.model;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderData {

    @NotNull(message = "id cant be null")
    private Integer id;

    @NotNull
    private String time;

    @NotNull
    private boolean orderPlaced=false;
}
