package com.example.ebshop.service;

import com.example.ebshop.dto.request.SavedBookDTO;
import com.example.ebshop.entity.Book;
import org.springframework.http.ResponseEntity;

public interface BookService {
    void saveBook(Book book);
    Book findBookById(String id);
    ResponseEntity<String> saveBookToStorage(SavedBookDTO book);
    ResponseEntity<String> deleteBookById(String id);
    ResponseEntity<?> getBookById(String id);
}