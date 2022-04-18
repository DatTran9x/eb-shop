package com.example.ebshop.service.impl;

import com.example.ebshop.dto.response.PublisherAndBookDTO;
import com.example.ebshop.dto.response.PublisherDTO;
import com.example.ebshop.dto.response.TopSellingBooks;
import com.example.ebshop.entity.Publisher;
import com.example.ebshop.repository.PublisherRepository;
import com.example.ebshop.service.BookService;
import com.example.ebshop.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    BookService bookService;

    //Thêm NXB
    @Override
    public ResponseEntity<String> save(Publisher publisher) {
        if(publisher.getId()==null) return ResponseEntity.status(HttpStatus.OK).body("Not found ID");
        if(publisher.getName()==null) return ResponseEntity.status(HttpStatus.OK).body("Not found name");
        publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.OK).body("Add success!");
    }

    //tìm nhà xuất bản
    @Override
    public ResponseEntity<?> findById(String id) {
        PublisherDTO publisher = publisherRepository.findPublisherById(PublisherDTO.class,id);
        PublisherAndBookDTO publisherAndBookDTO = new PublisherAndBookDTO();
        publisherAndBookDTO.setPublisherDTO(publisher);
        if(ObjectUtils.isEmpty(publisher)) return ResponseEntity.status(HttpStatus.OK).body("Not found!");
        List<TopSellingBooks> bookDTO = bookService.find5BestSellingBook(publisher);
        publisherAndBookDTO.setTopSellingBooks(bookDTO);
        Long count = bookService.getCountOfBookByPublisherId(publisher.getId());
        publisherAndBookDTO.setCount(count);
        return new ResponseEntity<>(publisherAndBookDTO, HttpStatus.OK);
    }

    //Update NXB
    @Override
    public ResponseEntity<String> update(Publisher publisher) {
        if(publisher.getId()==null) return ResponseEntity.status(HttpStatus.OK).body("Not found id!");
        PublisherDTO publisherDTO = publisherRepository.findPublisherById(PublisherDTO.class, publisher.getId());
        if(ObjectUtils.isEmpty(publisherDTO)) return ResponseEntity.status(HttpStatus.OK).body("Not found publisher!");
        if(publisher.getName()==null){
            publisher.setName(publisherDTO.getName());
        }
        publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.OK).body("Add success!");
    }

    //Xóa NXB
    @Override
    public ResponseEntity<String> deletePublisher(String id) {
        PublisherDTO publisherDTO = publisherRepository.findPublisherById(PublisherDTO.class, id);
        if(ObjectUtils.isEmpty(publisherDTO)) return ResponseEntity.status(HttpStatus.OK).body("Not found!");
        if(bookService.checkQuantity(publisherDTO.getId())) return ResponseEntity.status(HttpStatus.OK).body("There are still books in storage!");
        bookService.removePublisher(publisherDTO.getId());
        publisherRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success!");
    }
}
