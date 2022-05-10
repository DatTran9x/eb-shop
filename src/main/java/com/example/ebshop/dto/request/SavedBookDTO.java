package com.example.ebshop.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//DTO sách từ request vào để update hoặc thêm sách

@Data
public class SavedBookDTO {
    private String id;
    private String name;
    private AuthorDTO author;
    private PublisherDTO publisher;
    private BigDecimal price;
    private Date publishedYear;
    private Long quantity;

    public static class AuthorDTO{
        private String id;

        public String getId() {
            return id;
        }
    }

    public static class PublisherDTO{
        private String id;

        public String getId() {
            return id;
        }
    }
}
