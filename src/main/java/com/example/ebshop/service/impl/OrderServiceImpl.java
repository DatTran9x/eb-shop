package com.example.ebshop.service.impl;

import com.example.ebshop.dto.request.OrderDetailDTO;
import com.example.ebshop.dto.request.SaveOrderDTO;
import com.example.ebshop.dto.response.ShowOrderDTO;
import com.example.ebshop.entity.Customer;
import com.example.ebshop.entity.OrderDetail;
import com.example.ebshop.entity.Orders;
import com.example.ebshop.repository.OrdersRepository;
import com.example.ebshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    //Them order
    @Transactional
    @Override
    public ResponseEntity<String> saveOrder(SaveOrderDTO saveOrderDTO) {
        for (OrderDetailDTO orderDetails : saveOrderDTO.getOrderDetails()) {
            if (ObjectUtils.isEmpty(orderDetails)) return ResponseEntity.status(HttpStatus.OK).body("Missing order!");
            if (ObjectUtils.isEmpty(orderDetails.getBook()))
                return ResponseEntity.status(HttpStatus.OK).body("Missing book!");
        }
        if (ObjectUtils.isEmpty(saveOrderDTO.getCustomer()))
            return ResponseEntity.status(HttpStatus.OK).body("Missing customer!");
        for (OrderDetailDTO orderDetailDTO : saveOrderDTO.getOrderDetails()) {
            if (!bookService.isEnoughBook(orderDetailDTO.getBook())) {
                return ResponseEntity.status(HttpStatus.OK).body(orderDetailDTO.getBook().getId() + " not enough book!");
            }
            if (bookService.isDeleted(orderDetailDTO.getBook().getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(orderDetailDTO.getBook().getId() + " got deleted!");
            }
        }
        Customer customer = customerService.findByID(saveOrderDTO.getCustomer().getEmail());
        if (ObjectUtils.isEmpty(customer)) customer = customerService.save(saveOrderDTO.getCustomer());
        List<OrderDetail> orderDetails = orderDetailService.save(saveOrderDTO.getOrderDetails());
        String generatedString;
        BigDecimal totalPrice = new BigDecimal("0");
        do {
            generatedString = UUID.randomUUID().toString();
        }
        while (ordersRepository.existsById(generatedString));
        for (OrderDetail orderDetail : orderDetails) {
            BigDecimal price = orderDetail.getBook().getPrice();
            price = price.multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
            totalPrice = totalPrice.add(price);
        }
        Orders order = new Orders(generatedString, customer, orderDetails, totalPrice);
        ordersRepository.save(order);
        bookService.soldBook(orderDetails);
        authorService.soldBook(orderDetails);
        publisherService.soldBook(orderDetails);
        customerService.buyBook(orderDetails, customer.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("Add success!");
    }

    //Lay ra order
    @Override
    public ResponseEntity<?> getOrder(String id) {
        if (!ordersRepository.existsById(id)) return ResponseEntity.status(HttpStatus.OK).body("Wrong id!");
        ShowOrderDTO showOrderDTO = ordersRepository.findAllById(ShowOrderDTO.class, id);
        return ResponseEntity.status(HttpStatus.OK).body(showOrderDTO);
    }
}
