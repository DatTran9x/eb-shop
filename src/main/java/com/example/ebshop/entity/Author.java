package com.example.ebshop.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Author {
    @Id
    @Column(length = 254)
    private String id ;
    private String name;
    private String website;
    private String organization;
    private Long totalQuantitySold;

    public Author() {
    }

    public Author(String id, String name, String website, String organization, Long totalQuantitySold) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.organization = organization;
        this.totalQuantitySold = totalQuantitySold;
    }
}
