package com.example.ebshop.repository;

import com.example.ebshop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,String> {
    <T>T findAllById(Class<T> classType,String id);
}
