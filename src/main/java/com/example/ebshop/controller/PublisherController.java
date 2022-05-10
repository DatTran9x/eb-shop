package com.example.ebshop.controller;

import com.example.ebshop.dto.request.SavePublisherDTO;
import com.example.ebshop.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @PostMapping("/save")
    public ResponseEntity<String> savePublisher(@RequestBody SavePublisherDTO publisher){
        return publisherService.save(publisher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisher(@PathVariable String id){
        return publisherService.findById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePublisher(@RequestBody SavePublisherDTO publisher){
        return publisherService.update(publisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable String id){
        return publisherService.deletePublisher(id);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<?> getBestseller(){
        return publisherService.getBestseller();
    }
}
