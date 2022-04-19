package com.example.ebshop.service;

import com.example.ebshop.dto.request.AuthorDTO;
import com.example.ebshop.entity.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {
    ResponseEntity<String> saveAuthor(AuthorDTO author);
    ResponseEntity<?> getAuthorById(String id);
    ResponseEntity<String> updateAuthor(AuthorDTO authorDTO);
    ResponseEntity<String> deleteAuthor(String id);
    ResponseEntity<?> getFiveBestSeller();
    void soldBook(List<OrderDetail> orderDetails);
}
