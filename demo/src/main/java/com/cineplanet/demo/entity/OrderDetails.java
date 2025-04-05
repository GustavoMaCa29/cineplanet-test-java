package com.cineplanet.demo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
public class OrderDetails extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Orders orders;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
}
