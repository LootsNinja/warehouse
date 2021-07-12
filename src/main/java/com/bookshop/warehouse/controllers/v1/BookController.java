package com.bookshop.warehouse.controllers.v1;

import com.bookshop.warehouse.models.Book;
import com.bookshop.warehouse.repository.BookReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bookshop.warehouse.constants.BookConstants.BOOK_END_POINT_V1;

@Slf4j
@RestController
public class BookController {

    @Autowired
    BookReactiveRepository bookReactiveRepository;

    @GetMapping(BOOK_END_POINT_V1)
    public Flux<Book> getAllBooks(){
        return bookReactiveRepository.findAll();
    }

    @GetMapping(BOOK_END_POINT_V1+"/{id}")
    public Mono<ResponseEntity<Book>> getOneItem(@PathVariable String id){
        return bookReactiveRepository.findById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(BOOK_END_POINT_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> createItem(@RequestBody Book book){
        return bookReactiveRepository.save(book);
    }
}
