package com.bookshop.warehouse.repository;

import com.bookshop.warehouse.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class BookReactiveRepositoryTest {
    @Autowired BookReactiveRepository bookReactiveRepository;

    List<Book> booksList =
            Arrays.asList(
                    Book.builder().author("First Author").isbn("123").title("Some interesting title").build(),
                    Book.builder().author("Second Author").isbn("456").title("Some boring title").build(),
                    Book.builder().author("Third Author").isbn("789").title("Some thrilling title").build(),
                    Book.builder().author("Fourth Author").isbn("012").title("Some scary title").build(),
                    Book.builder().author("Fifth Author").isbn("345").title("Some noob title").id("1").build());

    @Before
    public void setUp(){
        bookReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(booksList))
                .flatMap(bookReactiveRepository::save)
                .blockLast();
    }

    @Test
    public void getAllBooks(){
        StepVerifier.create(bookReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void getBookById(){
        StepVerifier.create(bookReactiveRepository.findById("1"))
                .expectSubscription()
                .expectNextMatches(book -> book.getId().equals("1") && book.getTitle().equals("Some noob title"))
                .verifyComplete();
    }

    @Test
    public void findBookByDescription(){
        StepVerifier.create(bookReactiveRepository.findBookByAuthor("Third Author"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void saveBook(){
        Book book = Book.builder().author("Sixth Author").isbn("678").title("Some stupid title").id("2").build();
        StepVerifier.create(bookReactiveRepository.save(book))
                .expectSubscription()
                .expectNextMatches(book1 -> book1.getId().equals("2") && book1.getIsbn().equals("678"))
                .verifyComplete();
    }

    @Test
    public void updateBook(){
        String newAuthor = "new Second Author";
        Mono<Book> updatedBook = bookReactiveRepository.findBookByAuthor("Second Author")
                .map(book -> {
                    book.setAuthor(newAuthor);
                    return book;
                })
                .flatMap(book -> bookReactiveRepository.save(book));
        StepVerifier.create(updatedBook)
                .expectSubscription()
                .expectNextMatches(book -> book.getAuthor().equals(newAuthor))
                .verifyComplete();
    }

    @Test
    public void deleteBookById(){
        Mono<Void> deleteBookById = bookReactiveRepository.findById("1")
                .map(Book::getId)
                .flatMap(id -> bookReactiveRepository.deleteById(id));

        StepVerifier.create(deleteBookById)
                .expectSubscription()
                .verifyComplete();

        StepVerifier.create(bookReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void deleteBookByAuthor(){
        Mono<Void> deleteBookByAuthor = bookReactiveRepository.findBookByAuthor("Fourth Author")
                .flatMap(book -> bookReactiveRepository.delete(book));

        StepVerifier.create(deleteBookByAuthor)
                .expectSubscription()
                .verifyComplete();

        StepVerifier.create(bookReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }
}
