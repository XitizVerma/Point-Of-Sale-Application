package com.increff.pos.pojo;

//javax.persistence v krr skte

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

import static com.increff.pos.pojo.TableConstants.BRAND_GENERATOR;
import static com.increff.pos.pojo.TableConstants.BRAND_INITIAL_VALUE;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"brand", "category"})}, name = "pos_brand_pojo")
public class BrandPojo {

    @Id
    @TableGenerator(name = BRAND_GENERATOR, initialValue = BRAND_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = BRAND_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;


}
