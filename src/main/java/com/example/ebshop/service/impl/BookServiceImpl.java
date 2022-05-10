package com.example.ebshop.service.impl;

import com.example.ebshop.dto.request.AuthorDTO;
import com.example.ebshop.dto.request.OrderBookDTO;
import com.example.ebshop.dto.request.SavedBookDTO;
import com.example.ebshop.dto.response.BookDetailsDTO;
import com.example.ebshop.dto.response.PublisherDTO;
import com.example.ebshop.dto.response.TopSellingBooks;
import com.example.ebshop.dto.response.UpdatedBookDTO;
import com.example.ebshop.entity.Book;
import com.example.ebshop.entity.OrderDetail;
import com.example.ebshop.repository.BookRepository;
import com.example.ebshop.service.AuthorService;
import com.example.ebshop.service.BookService;
import com.example.ebshop.service.PublisherService;
import com.example.ebshop.specification.model.PageType;
import com.example.ebshop.specification.model.Search;
import com.example.ebshop.specification.model.SortType;
import com.example.ebshop.specification.service.BookSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Lazy
    @Autowired
    private AuthorService authorService;

    @Lazy
    @Autowired
    private PublisherService publisherService;

    // lưu lại sách
    @Transactional
    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    //Tìm sách theo id
    @Override
    public Book findBookById(String id) {
        return bookRepository.findById(id).get();
    }

    //Kiểm tra xem sách đã tồn tại chưa
    private boolean isBookExist(String id) {
        return bookRepository.existsById(id);
    }

    //Cập nhật lại sách đã tồn tại
    private void updateExistingBook(SavedBookDTO book) {
        Book oldBook = findBookById(book.getId());
        if (Boolean.TRUE.equals(oldBook.getDeleted())) return;
        transferDataFromSaveBookToBook(book, oldBook);
        saveBook(oldBook);
    }

    // lưu sách mới
    private void saveNewBook(SavedBookDTO newBook) {
        Book book = new Book();
        if (newBook.getId() == null) {
            String generatedString;
            do {
                generatedString = UUID.randomUUID().toString();
            }
            while (bookRepository.existsById(generatedString));
            book.setId(generatedString);
        } else book.setId(newBook.getId());
        transferDataFromSaveBookToBook(newBook, book);
        book.setDeleted(false);
        book.setQuantitySold(0L);
        saveBook(book);
    }

    // Chuyển dữ liệu từ DTO
    private void transferDataFromSaveBookToBook(SavedBookDTO book, Book oldBook) {
        if (book.getName() != null) {
            oldBook.setName(book.getName());
        }
        if (book.getAuthor() != null) {
            oldBook.setAuthor(authorService.findById(book.getAuthor().getId()));
        }
        if (book.getPublisher() != null) {
            oldBook.setPublisher(publisherService.findPublisherById(book.getPublisher().getId()));
        }
        if (book.getPrice() != null) {
            oldBook.setPrice(book.getPrice());
        }
        if (book.getPublishedYear() != null) {
            oldBook.setPublishedYear(book.getPublishedYear());
        }
        if (book.getQuantity() != null) {
            if (oldBook.getQuantityCurrent() == null) {
                oldBook.setQuantityCurrent(0L);
            }
            oldBook.setQuantityCurrent(oldBook.getQuantityCurrent() + book.getQuantity());
        }
    }

    //Lấy ra Dto của sách từ ID
    private UpdatedBookDTO getBookByIdToUpdate(String id) {
        Book book = findBookById(id);
        if (Boolean.TRUE.equals(book.getDeleted())) return null;
        return bookRepository.findAllById(UpdatedBookDTO.class, id);
    }

    // Đổi delete từ false sang true
    private void softDeleteBookById(String id) {
        bookRepository.softDeleteBookById(id);
    }

    // Check xem đã xóa chưa
    @Override
    public boolean isDeleted(String id) {
        return bookRepository.isDeleted(id);
    }

    // Lấy ra 10 quyển sách bán chạy nhất
    @Override
    public ResponseEntity<?> tenBestSellingBook() {
        Page<BookDetailsDTO> page = bookRepository.tenBestSellingBook(BookDetailsDTO.class, PageRequest.of(0, 10));
        List<BookDetailsDTO> bookDetailsDTO = page.getContent();
        return ResponseEntity.status(HttpStatus.OK).body(bookDetailsDTO);
    }

    @Override
    public ResponseEntity<?> search(Search search, SortType sortType, PageType pageType) {
        BookSpecificationBuilder builder = new BookSpecificationBuilder(search);
        Sort sort = sortBy(sortType);
        if(builder.build()==null){
            return ResponseEntity.status(HttpStatus.OK).body("Please fill something!");
        }
        Page<Book> books = bookRepository.findAll(builder.build(), PageRequest.of(pageType.getPage(), pageType.getSize(), sort));
        List<BookDetailsDTO> listDTO = new ArrayList<>();
        for (Book book:books.getContent()) {
            BookDetailsDTO bookDetailsDTO = new BookDetailsDTO(
                    book.getId(), book.getName(), new BookDetailsDTO.AuthorDTO(book.getAuthor().getId(),book.getAuthor().getName()),
                    new BookDetailsDTO.PublisherDTO(book.getPublisher().getId(), book.getPublisher().getName()),
                    book.getQuantityCurrent(),book.getPrice());
            listDTO.add(bookDetailsDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listDTO);
    }

    private Sort sortBy(SortType sortType) {
        if (sortType.getPrice() != 0) {
            if (sortType.getPrice() == 1)
                return Sort.by("price").descending();
            else return Sort.by("price").ascending();
        }
        if (sortType.getBuy() != 0) {
            if (sortType.getBuy() == 1)
                return Sort.by("quantitySold").descending();
            else return Sort.by("quantitySold").ascending();
        }
        if (sortType.getName() == 1)
            return Sort.by("name").descending();
        else return Sort.by("name").ascending();
    }

    // Lưu sách hoặc update sách
    @Override
    public ResponseEntity<String> saveBookToStorage(SavedBookDTO book) {
        if (isDeleted(book.getId())) return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
        if (isBookExist(book.getId())) {
            updateExistingBook(book);
            return ResponseEntity.status(HttpStatus.OK).body("Updated success!");
        } else {
            saveNewBook(book);
            return ResponseEntity.status(HttpStatus.OK).body("Added success!");
        }
    }

    //Soft delete sách
    @Override
    public ResponseEntity<String> deleteBookById(String id) {
        //Kiểm tra xem sách có tồn tại hay đã bị soft delete không?
        if (!isBookExist(id)) return ResponseEntity.status(HttpStatus.OK).body("Not found!");
        if (isDeleted(id)) return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
        softDeleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success!");
    }

    // Tìm sách theo Id
    @Override
    public ResponseEntity<?> getBookById(String id) {
        UpdatedBookDTO book = getBookByIdToUpdate(id);
        if (book == null) return ResponseEntity.status(HttpStatus.OK).body("Not found!");
        else return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Lấy ra 3 cuốn sách bán chạy nhất
    @Override
    public List<TopSellingBooks> find3MostSoldBook(String id) {
        return bookRepository.find3MostSoldBook(TopSellingBooks.class, id);
    }

    //Lấy ra số lượng sách
    @Override
    public Long getNumberOfBooks(AuthorDTO authorDTO) {
        return bookRepository.countByAuthor(authorDTO.getId());
    }

    //Lấy ra số lượng sách
    @Override
    public boolean checkPublisher(String id) {
        return bookRepository.checkPublisher(id);
    }

    //Lấy ra 5 thằng bán chạy nhất tại NXB
    @Override
    public List<TopSellingBooks> find5BestSellingBook(PublisherDTO publisher) {
        return bookRepository.find5BestSellingBook(TopSellingBooks.class, publisher.getId());
    }

    //Đếm số sách của NXB
    @Override
    public Long getCountOfBookByPublisherId(String id) {
        return bookRepository.countByPublisherId(id);
    }

    //Kiểm tra đủ sách ko
    @Override
    public boolean isEnoughBook(OrderBookDTO book) {
        return bookRepository.isEnoughBook(book.getId());
    }

    //Thêm sách đã bán và giảm sách hiện tại
    @Override
    public void soldBook(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            bookRepository.addSoldBook(orderDetail.getQuantity(), orderDetail.getBook().getId());
        }
    }
}
