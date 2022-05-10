package com.example.ebshop.service;

import com.example.ebshop.dto.request.OrderDetailDTO;
import com.example.ebshop.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> save(List<OrderDetailDTO> orderDetails);
}
