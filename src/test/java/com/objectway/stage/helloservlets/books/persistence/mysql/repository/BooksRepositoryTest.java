package com.objectway.stage.helloservlets.books.persistence.mysql.repository;

import com.objectway.stage.helloservlets.books.controller.BooksController;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.AuthorEntity;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.BookEntity;
import com.objectway.stage.helloservlets.books.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BooksRepositoryTest {
    @Autowired(required = false)
    private BookService bookService;

    @Autowired(required = false)
    private BooksController booksController;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Test
    public void onlyPersistenceBeansAreLoaded() {
        assertNull("Services should not be loaded", bookService);
        assertNull("Controllers should not be loaded", booksController);
    }

    @Test
    @Sql("/scripts/findByIsbn.sql")
    public void findByIsbn() {
        final Optional<BookEntity> retrieved = booksRepository.findByIsbn("1234");
        assertTrue("A book should be found", retrieved.isPresent());

        final Set<AuthorEntity> expectedAuthors = new HashSet<>();
        expectedAuthors.add(
            new AuthorEntity()
                .setId(1L)
                .setName("a1")
        );
        expectedAuthors.add(
            new AuthorEntity()
                .setId(2L)
                .setName("a2")
        );
        assertEquals(
            new BookEntity()
                .setIsbn("1234")
                .setTitle("t1")
                .setYear(1000)
                .setAuthors(expectedAuthors),
            retrieved.get()
        );
    }

    @Test
    @Sql("/scripts/deleteByIsbn.sql")
    public void deleteByIsbn() {
        booksRepository.deleteByIsbn("1234");

        final Optional<BookEntity> retrieved = booksRepository.findByIsbn("1234");
        assertFalse("A book should not be found", retrieved.isPresent());

        assertEquals("Authors should not be deleted", 3, authorsRepository.findAll().size());
    }
}
