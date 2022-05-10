package com.example.ebshop.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class OrderDetail {
    @Id
    @Column(length = 254)
    private String id;
    @ManyToOne
    private Book book;
    private Long quantity;

    public OrderDetail() {
    }

    public OrderDetail(String id, Book book, Long quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
    }
}
