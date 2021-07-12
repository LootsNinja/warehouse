package com.bookshop.warehouse.repository;

import com.bookshop.warehouse.models.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BookReactiveRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findBookByAuthor(String author);
}
