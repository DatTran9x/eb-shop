package com.example.ebshop.dto.request;

import lombok.Data;

@Data
public class OrderBookDTO {
    private String id;

    public OrderBookDTO() {
    }

    public OrderBookDTO(String id) {
        this.id = id;
    }
}
