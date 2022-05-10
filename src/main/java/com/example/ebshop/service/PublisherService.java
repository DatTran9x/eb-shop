package com.example.ebshop.service;

import com.example.ebshop.dto.request.SavePublisherDTO;
import com.example.ebshop.entity.OrderDetail;
import com.example.ebshop.entity.Publisher;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublisherService {
    ResponseEntity<String> save(SavePublisherDTO publisher);
    ResponseEntity<?> findById(String id);
    ResponseEntity<String> update(SavePublisherDTO publisher);
    ResponseEntity<String> deletePublisher(String id);
    ResponseEntity<?> getBestseller();
    void soldBook(List<OrderDetail> orderDetails);
    Publisher findPublisherById(String id);
}
