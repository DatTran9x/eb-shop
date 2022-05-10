package com.example.ebshop.repository;

import com.example.ebshop.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher,String> {
    <T>T findPublisherById(Class<T> classType, String id);
    <T>List<T> findAllBy(Class<T> classType);
    @Modifying
    @Transactional
    @Query("update Publisher set totalQuantitySold = totalQuantitySold+?1 where id=?2")
    void soldBook(Long quantity, String id);
}
