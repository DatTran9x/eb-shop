package com.example.ebshop.service;

import com.example.ebshop.dto.request.SaveOrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrdersService {
    ResponseEntity<String> saveOrder(SaveOrderDTO saveOrderDTO);
    ResponseEntity<?> getOrder(String id);
}
