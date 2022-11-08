package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;

@Getter
@Setter
public class ProductForm {

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String barcode;

    @Column(nullable=false)
    private String brand;

    @Column(nullable=false)
    private String category;

    @Column(nullable = false)
    Double mrp;
}