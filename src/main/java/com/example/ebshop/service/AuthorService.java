package com.example.ebshop.service;

import com.example.ebshop.dto.request.SaveAuthorDTO;
import com.example.ebshop.entity.Author;
import com.example.ebshop.entity.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {
    ResponseEntity<String> saveAuthor(SaveAuthorDTO author);
    ResponseEntity<?> getAuthorById(String id);
    ResponseEntity<String> updateAuthor(SaveAuthorDTO authorDTO);
    ResponseEntity<String> deleteAuthor(String id);
    ResponseEntity<?> getFiveBestSeller();
    void soldBook(List<OrderDetail> orderDetails);
    Author findById(String id);
}
