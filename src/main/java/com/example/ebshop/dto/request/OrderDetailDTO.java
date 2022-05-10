package com.example.ebshop.dto.request;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private OrderBookDTO book;
    private Long quantity;
}
