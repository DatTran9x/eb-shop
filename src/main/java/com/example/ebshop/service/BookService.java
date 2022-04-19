package com.example.ebshop.service;

import com.example.ebshop.dto.request.AuthorDTO;
import com.example.ebshop.dto.request.BookDTO;
import com.example.ebshop.dto.request.SavedBookDTO;
import com.example.ebshop.dto.response.PublisherDTO;
import com.example.ebshop.dto.response.TopSellingBooks;
import com.example.ebshop.entity.Book;
import com.example.ebshop.entity.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    void saveBook(Book book);
    Book findBookById(String id);
    ResponseEntity<String> saveBookToStorage(SavedBookDTO book);
    ResponseEntity<String> deleteBookById(String id);
    ResponseEntity<?> getBookById(String id);
    List<TopSellingBooks> find3MostSoldBook(String id);
    Long getNumberOfBooks(AuthorDTO authorDTO);
    boolean checkPublisher(String id);
    List<TopSellingBooks> find5BestSellingBook(PublisherDTO publisher);
    Long getCountOfBookByPublisherId(String id);
    boolean isEnoughBook(BookDTO book);
    void soldBook(List<OrderDetail> orderDetails);
    boolean isDeleted(String id);
}
