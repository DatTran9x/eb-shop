package com.example.ebshop.dto.response;

import lombok.Data;

import java.math.BigDecimal;

public interface BestCustomerDTO {
    String getName();
    String getEmail();
    String getPhone();
    String getTotalBoughtBook();
    String getTotalMoneySpent();
}
