package com.example.ebshop.service.impl;

import com.example.ebshop.dto.SortForAuthor;
import com.example.ebshop.dto.request.AuthorDTO;
import com.example.ebshop.dto.request.SaveAuthorDTO;
import com.example.ebshop.dto.response.AuthorAdnBookDTO;
import com.example.ebshop.dto.response.TopSellingBooks;
import com.example.ebshop.entity.Author;
import com.example.ebshop.entity.OrderDetail;
import com.example.ebshop.repository.AuthorRepository;
import com.example.ebshop.service.AuthorService;
import com.example.ebshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    //Lưu hoặc cập nhật tác giả
    @Override
    public ResponseEntity<String> saveAuthor(SaveAuthorDTO authorDTO) {
        if (ObjectUtils.isEmpty(authorDTO)) {
            return ResponseEntity.status(HttpStatus.OK).body("Author is null!");
        }
        Author author = new Author(authorDTO.getId(), authorDTO.getName(),authorDTO.getWebsite(),authorDTO.getOrganization(),0L);
        authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.OK).body("Add success!");
    }

    //Tìm tác giả từ ID
    @Override
    public ResponseEntity<?> getAuthorById(String id) {
        if (ObjectUtils.isEmpty(authorRepository.findById(id)))
            return ResponseEntity.status(HttpStatus.OK).body("Author is not exist!");
        AuthorDTO authorDTO = authorRepository.findAuthorById(AuthorDTO.class, id);
        AuthorAdnBookDTO authorAdnBookDTO = new AuthorAdnBookDTO();
        List<TopSellingBooks> book = bookService.find3MostSoldBook(authorDTO.getId());
        authorAdnBookDTO.setAuthor(authorDTO);
        authorAdnBookDTO.setBook(book);
        authorAdnBookDTO.setNumberOfBooks(bookService.getNumberOfBooks(authorDTO));
        return new ResponseEntity<>(authorAdnBookDTO, HttpStatus.OK);
    }

    //Cập nhật tác giả
    @Override
    public ResponseEntity<String> updateAuthor(SaveAuthorDTO authorDTO) {
        return saveAuthor(authorDTO);
    }

    //Xóa tác giả
    @Override
    public ResponseEntity<String> deleteAuthor(String id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Delete Success!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Not found author!");
        }
    }

    // Lấy 5 tác giả sách bán chạy nhất
    @Override
    public ResponseEntity<?> getFiveBestSeller() {
        List<AuthorDTO> list = authorRepository.findAllBy(AuthorDTO.class);
        list.sort(new SortForAuthor());
        if(list.size()>5) return ResponseEntity.status(HttpStatus.OK).body(list.subList(0,4));
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Override
    public void soldBook(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            authorRepository.soldBook(orderDetail.getQuantity(),orderDetail.getBook().getAuthor().getId());
        }
    }

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id).get();
    }
}
