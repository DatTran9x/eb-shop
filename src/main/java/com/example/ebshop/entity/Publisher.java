package com.example.ebshop.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Publisher {
    @Id
    @Column(length = 254)
    private String id ;
    private String name;
    private String website;
    private String address;
    private Long totalQuantitySold;

    public Publisher() {
    }

    public Publisher(String id, String name, String website, String address, Long totalQuantitySold) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.address = address;
        this.totalQuantitySold = totalQuantitySold;
    }
}
