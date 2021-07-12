package com.bookshop.warehouse.repository;

import com.bookshop.warehouse.models.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookReactiveRepository extends ReactiveMongoRepository<Book, String> {

}
