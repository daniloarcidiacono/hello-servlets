package com.objectway.stage.helloservlets.books.service;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.AuthorEntity;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.BookEntity;
import com.objectway.stage.helloservlets.books.persistence.mysql.repository.AuthorsRepository;
import com.objectway.stage.helloservlets.books.persistence.mysql.repository.BooksRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class MySQLBookServiceTest {
    @TestConfiguration
    static class Config {
        @Bean
        public MySQLBookService mySQLBookService(final BooksRepository booksRepository, final AuthorsRepository authorsRepository) {
            return new MySQLBookService(booksRepository, authorsRepository);
        }
    }

    private final Set<AuthorEntity> stubAuthors = new HashSet<>();
    private final List<BookEntity> stubBooks = Arrays.asList(
        new BookEntity()
            .setIsbn("1")
            .setTitle("t1")
            .setYear(1)
            .setAuthors(stubAuthors)
    );

    @Autowired
    private MySQLBookService bookService;

    @MockBean
    private BooksRepository booksRepository;

    @MockBean
    private AuthorsRepository authorsRepository;

    public MySQLBookServiceTest() {
        stubAuthors.add(
            new AuthorEntity()
                .setId(1L)
                .setName("a1")
        );

        stubAuthors.add(
            new AuthorEntity()
                .setId(2L)
                .setName("a2")
        );
    }

    @Test
    public void getAllBooks() {
        Mockito.when(booksRepository.findAll()).thenReturn(stubBooks);

        assertEquals(
            Arrays.asList(
                new Book()
                    .setIsbn("1")
                    .setTitle("t1")
                    .setYear(1)
                    .setAuthors(Arrays.asList("a1", "a2"))
            ),
            bookService.getAllBooks()
        );
    }

    @Test
    public void searchBookByISBN() {
    }

    @Test
    public void saveBook() {
    }

    @Test
    public void deleteBookByISBN() {
    }
}
