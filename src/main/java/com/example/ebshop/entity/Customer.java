package com.example.ebshop.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Entity
@Data
public class Customer {
    @Id
    @Column(length = 254)
    @Email
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String phone;
    private Long totalBoughtBook;
    private BigDecimal totalMoneySpent;

    public Customer() {
    }

    public Customer(String email, String name, String phone, Long totalBoughtBook, BigDecimal totalMoneySpent) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.totalBoughtBook = totalBoughtBook;
        this.totalMoneySpent = totalMoneySpent;
    }
}
