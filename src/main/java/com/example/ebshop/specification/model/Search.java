package com.example.ebshop.specification.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Search {
    private String bookName;
    private String bookId;
    private String authorId;
    private String publisherId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
