package com.example.ebshop.controller;


import com.example.ebshop.dto.request.SaveAuthorDTO;
import com.example.ebshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    //Lưu tác giả vào
    @PostMapping("/save")
    public ResponseEntity<String> saveAuthor(@RequestBody SaveAuthorDTO authorDTO) {
       return authorService.saveAuthor(authorDTO);
    }

    //Sửa thông tin tác giả
    @PutMapping("/update")
    public ResponseEntity<String> updateAuthor(@RequestBody SaveAuthorDTO authorDTO) {
        return authorService.updateAuthor(authorDTO);
    }

    //Xóa tác giả
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable String id){
        return authorService.deleteAuthor(id);
    }

    //Xem thông tin tác giả
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable String id){
        return authorService.getAuthorById(id);
    }

    //Xem 5 tác giả nhiều lượt view nhất
    @GetMapping("/best-seller")
    public ResponseEntity<?> getFiveBestSeller(){
        return authorService.getFiveBestSeller();
    }
}
