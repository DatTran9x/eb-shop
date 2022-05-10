package com.example.ebshop.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @Column(length = 254)
    private String id;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<OrderDetail> orderDetails;
    private BigDecimal totalPrice;

    public Orders() {
    }

    public Orders(String id, Customer customer, List<OrderDetail> orderDetails, BigDecimal totalPrice) {
        this.id = id;
        this.customer = customer;
        this.orderDetails = orderDetails;
        this.totalPrice = totalPrice;
    }
}
