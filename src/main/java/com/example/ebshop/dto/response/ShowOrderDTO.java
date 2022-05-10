package com.example.ebshop.dto.response;

import java.math.BigDecimal;
import java.util.List;

public interface ShowOrderDTO {
    CustomerDTO getCustomer();
    List<OrderDetailDTO> getOrderDetails();
    BigDecimal getTotalPrice();

    interface CustomerDTO {
        String getEmail();
        String getName();
        String getPhone();
    }

    interface OrderDetailDTO{
        Long getQuantity();
        BookDTO getBook();
    }

    interface BookDTO{
        String getName();
        AuthorDTO getAuthor();
    }

    interface AuthorDTO{
        String getName();
    }
}
