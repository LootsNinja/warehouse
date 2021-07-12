package com.bookshop.warehouse.repository;

import com.bookshop.warehouse.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
public class BookReactiveRepositoryTest {
    @Autowired BookReactiveRepository bookReactiveRepository;

    List<Book> booksList =
            Arrays.asList(
                    Book.builder().Author("First Author").ISBN("123").Title("Some interesting title").build(),
                    Book.builder().Author("Second Author").ISBN("456").Title("Some boring title").build(),
                    Book.builder().Author("Third Author").ISBN("789").Title("Some thrilling title").build(),
                    Book.builder().Author("Fourth Author").ISBN("012").Title("Some scary title").build());

    @Before
    public void setUp(){
        bookReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(booksList))
                .flatMap(bookReactiveRepository::save)
                .doOnNext(item -> System.out.println("inserted item is: " + item))
                .blockLast();
    }

    @Test
    public void getAllItems(){
        StepVerifier.create(bookReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }
}
