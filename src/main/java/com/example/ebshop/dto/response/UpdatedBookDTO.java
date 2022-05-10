package com.example.ebshop.dto.response;

import java.math.BigDecimal;
import java.util.Date;

//DTO để lấy sách ra từ Id cho update
public interface UpdatedBookDTO {
    String getId();
    String getName();
    authorDTO getAuthor();
    publisherDTO getPublisher();
    BigDecimal getPrice();
    Date getPublishedYear();
    Long getQuantityCurrent();

    interface authorDTO{
        String getId();
        String getName();
    }
    interface publisherDTO{
        String getId();
        String getName();
    }
}
