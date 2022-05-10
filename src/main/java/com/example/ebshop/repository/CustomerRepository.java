package com.example.ebshop.repository;

import com.example.ebshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Modifying
    @Transactional
    @Query("update Customer set totalBoughtBook = totalBoughtBook+?1 , totalMoneySpent = totalMoneySpent+?2 where email=?3")
    void buyBook(Long quantity, BigDecimal price,String email);

    <T> List<T> findAllBy(Class<T> classType);
}
