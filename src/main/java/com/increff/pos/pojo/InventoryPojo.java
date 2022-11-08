package com.increff.pos.pojo;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.increff.pos.pojo.TableConstants.INVENTORY_GENERATOR;
import static com.increff.pos.pojo.TableConstants.INVENTORY_INITIAL_VALUE;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})},name="pos_inventory_pojo")
public class InventoryPojo extends  AbstractPojo{

    @Id
    @TableGenerator(name=INVENTORY_GENERATOR,initialValue = INVENTORY_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = INVENTORY_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private Integer quantity;


}
