package com.example.ebshop.dto.response;

import com.example.ebshop.dto.request.AuthorDTO;
import lombok.Data;
import java.util.List;

@Data
public class AuthorAdnBookDTO {
    private AuthorDTO author;
    private List<TopSellingBooks> book;
    private Long numberOfBooks;
}
