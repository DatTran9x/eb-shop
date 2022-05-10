package com.example.ebshop.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDetailsDTO {
    private String id;
    private String name;
    private AuthorDTO author;
    private PublisherDTO publisher;
    private Long quantityCurrent;
    private BigDecimal price;

    @Data
    public static class AuthorDTO {
        private String id;
        private String name;

        public AuthorDTO(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    public static class PublisherDTO {
        private String id;
        private String name;

        public PublisherDTO(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public BookDetailsDTO(String id, String name, AuthorDTO author, PublisherDTO publisher, Long quantityCurrent, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantityCurrent = quantityCurrent;
        this.price = price;
    }
}
