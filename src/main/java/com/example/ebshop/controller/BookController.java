package com.example.ebshop.controller;

import com.example.ebshop.dto.request.SavedBookDTO;
import com.example.ebshop.service.BookService;
import com.example.ebshop.specification.model.PageType;
import com.example.ebshop.specification.model.Search;
import com.example.ebshop.specification.model.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    //Update va thêm sách vào kho
    @PostMapping("/save")
    public ResponseEntity<String> saveBook(@RequestBody SavedBookDTO book){
        return bookService.saveBookToStorage(book);
    }

    //Tìm sách theo Id sách
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id){
       return bookService.getBookById(id);
    }

    //Soft delete sách theo Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable String id){
        return bookService.deleteBookById(id);
    }

    //10 đầu sách bán chạy nhất
    @GetMapping("/best-seller")
    public ResponseEntity<?> tenBestSellingBook(){
        return bookService.tenBestSellingBook();
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(Search search, SortType sortType, PageType pageType){
        return bookService.search(search,sortType,pageType);
    }
}
