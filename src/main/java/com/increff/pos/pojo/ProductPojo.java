package com.increff.pos.pojo;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.increff.pos.pojo.TableConstants.PRODUCT_GENERATOR;
import static com.increff.pos.pojo.TableConstants.PRODUCT_INITIAL_VALUE;


@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})},name="pos_product_pojo")
public class ProductPojo extends AbstractPojo {

    @Id
    @TableGenerator(name = PRODUCT_GENERATOR, initialValue = PRODUCT_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PRODUCT_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private Integer brandCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double mrp;


}
