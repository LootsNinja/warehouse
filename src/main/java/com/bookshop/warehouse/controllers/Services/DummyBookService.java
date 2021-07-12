package com.bookshop.warehouse.controllers.Services;

import com.bookshop.warehouse.models.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dummyBook")
public class DummyBookService {

    @GetMapping
    public Mono<Book> getDummyBook(){
        Book book = Book.builder().author("Mansour").isbn("123-456-789-0123").title("The war of peace").build();
        return Mono.just(book);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Book> dummyBookFromBody(@RequestBody Book book){
        return Mono.just(book);
    }
}
