package com.example.ebshop.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaveOrderDTO {
    private CustomerDTO customer;
    private List<OrderDetailDTO> orderDetails;
    private BigDecimal totalPrice;
}
