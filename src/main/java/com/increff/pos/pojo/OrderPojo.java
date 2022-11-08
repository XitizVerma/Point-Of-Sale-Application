package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.ZonedDateTime;

import static com.increff.pos.pojo.TableConstants.ORDER_GENERATOR;
import static com.increff.pos.pojo.TableConstants.ORDER_INTIAL_VALUE;

@Getter
@Setter
@Entity

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})},name="pos_order_pojo")
public class OrderPojo extends AbstractPojo{

    @TableGenerator(name=ORDER_GENERATOR, initialValue = ORDER_INTIAL_VALUE)
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = ORDER_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private ZonedDateTime time;

    @Column(nullable = false)
    private Boolean orderPlaced=false;
}
