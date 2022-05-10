package com.example.ebshop.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class PublisherAndBookDTO {
    private PublisherDTO publisherDTO;
    private List<TopSellingBooks> TopSellingBooks;
    private Long count;
}
