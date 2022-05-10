package com.example.ebshop.service.impl;

import com.example.ebshop.dto.SortForPublisher;
import com.example.ebshop.dto.request.SavePublisherDTO;
import com.example.ebshop.dto.response.PublisherAndBookDTO;
import com.example.ebshop.dto.response.PublisherDTO;
import com.example.ebshop.dto.response.TopSellingBooks;
import com.example.ebshop.entity.OrderDetail;
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
    private PublisherRepository publisherRepository;

    @Autowired
    private BookService bookService;

    //Thêm NXB
    @Override
    public ResponseEntity<String> save(SavePublisherDTO publisherDTO) {
        if (publisherDTO.getId() == null) return ResponseEntity.status(HttpStatus.OK).body("Not found ID");
        if (publisherDTO.getName() == null) return ResponseEntity.status(HttpStatus.OK).body("Not found name");
        Publisher publisher = new Publisher(publisherDTO.getId(), publisherDTO.getName(),publisherDTO.getWebsite(),publisherDTO.getWebsite(),0L);
        publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.OK).body("Add success!");
    }

    //tìm nhà xuất bản
    @Override
    public ResponseEntity<?> findById(String id) {
        PublisherDTO publisher = publisherRepository.findPublisherById(PublisherDTO.class, id);
        PublisherAndBookDTO publisherAndBookDTO = new PublisherAndBookDTO();
        publisherAndBookDTO.setPublisherDTO(publisher);
        if (ObjectUtils.isEmpty(publisher)) return ResponseEntity.status(HttpStatus.OK).body("Not found!");
        List<TopSellingBooks> bookDTO = bookService.find5BestSellingBook(publisher);
        publisherAndBookDTO.setTopSellingBooks(bookDTO);
        Long count = bookService.getCountOfBookByPublisherId(publisher.getId());
        publisherAndBookDTO.setCount(count);
        return new ResponseEntity<>(publisherAndBookDTO, HttpStatus.OK);
    }

    //Update NXB
    @Override
    public ResponseEntity<String> update(SavePublisherDTO savePublisherDTO) {
        if (savePublisherDTO.getId() == null) return ResponseEntity.status(HttpStatus.OK).body("Not found id!");
        PublisherDTO publisherDTO = publisherRepository.findPublisherById(PublisherDTO.class, savePublisherDTO.getId());
        if (ObjectUtils.isEmpty(publisherDTO)) return ResponseEntity.status(HttpStatus.OK).body("Not found publisher!");
        if (savePublisherDTO.getName() == null) {
            savePublisherDTO.setName(publisherDTO.getName());
        }
        if(savePublisherDTO.getAddress()==null){
            savePublisherDTO.setAddress(publisherDTO.getAddress());
        }
        if(savePublisherDTO.getWebsite()==null){
            savePublisherDTO.setWebsite(publisherDTO.getWebsite());
        }
        Publisher publisher = new Publisher(savePublisherDTO.getId(), savePublisherDTO.getName(), savePublisherDTO.getWebsite(), savePublisherDTO.getAddress(),0L);
        publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.OK).body("Add success!");
    }

    //Xóa NXB
    @Override
    public ResponseEntity<String> deletePublisher(String id) {
        PublisherDTO publisherDTO = publisherRepository.findPublisherById(PublisherDTO.class, id);
        if (ObjectUtils.isEmpty(publisherDTO)) return ResponseEntity.status(HttpStatus.OK).body("Not found!");
        if (bookService.checkPublisher(publisherDTO.getId()))
            return ResponseEntity.status(HttpStatus.OK).body("There are still books in storage!");
        publisherRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success!");
    }

    @Override
    public ResponseEntity<?> getBestseller() {
        List<PublisherDTO> publishers = publisherRepository.findAllBy(PublisherDTO.class);
        publishers.sort(new SortForPublisher());
        if (publishers.size() > 5) return ResponseEntity.status(HttpStatus.OK).body(publishers.subList(0, 4));
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }

    @Override
    public void soldBook(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            publisherRepository.soldBook(orderDetail.getQuantity(), orderDetail.getBook().getPublisher().getId());
        }
    }

    @Override
    public Publisher findPublisherById(String id) {
        return publisherRepository.findById(id).get();
    }
}
