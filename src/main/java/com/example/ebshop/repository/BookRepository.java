package com.example.ebshop.repository;

import com.example.ebshop.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    @Modifying
    @Transactional
    @Query("update Book b set b.deleted = true where b.id=?1")
    void softDeleteBookById(String id);

    @Query("select case when count(id)>0 then true else false end from Book where id =?1 and deleted=true")
    Boolean isDeleted(String id);

    @Query(nativeQuery = true, value = "select name,id from book where author_id = ?1 limit 3")
    <T> List<T> find3MostSoldBook(Class<T> classType, String id);

    <T> T findAllById(Class<T> classType, String id);

    @Query("select count(id) from Book where author.id=?1 and deleted=false")
    Long countByAuthor(String id);

    @Query("select case when count(id)>0 then true else false end from Book where publisher.id =?1 and deleted=false")
    Boolean checkPublisher(String id);

    @Query(nativeQuery = true, value = "select name,id from book where publisher_id = ?1 limit 5")
    <T> List<T> find5BestSellingBook(Class<T> classType, String id);

    @Query("select count(id) from Book where publisher.id=?1")
    Long countByPublisherId(String id);

    @Query("select case when count(b)>0 then true else false end from Book b where b.id =?1 and b.quantityCurrent>0")
    boolean isEnoughBook(String id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update book set quantity_sold = quantity_sold+1, quantity_current = quantity_current-1 where id = 1 and quantity_current>0")
    void addSoldBook(Long quantity, String id);

    @Query("select b from Book b where b.deleted = false order by b.quantitySold desc")
    <T> Page<T> tenBestSellingBook(Class<T> classType, Pageable page);
}
