package com.objectway.stage.helloservlets.books.persistence.mongo.repository;

import com.objectway.stage.helloservlets.books.controller.BooksController;
import com.objectway.stage.helloservlets.books.persistence.mongo.entity.BookDocument;
import com.objectway.stage.helloservlets.books.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test for the MongoDB persistence layer.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
// For running against a real MongoDB instance
//@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class BooksMongoRepositoryTest {
    @Autowired
    private BooksMongoRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired(required = false)
    private BookService bookService;

    @Autowired(required = false)
    private BooksController booksController;

    @Test
    public void onlyPersistenceBeansAreLoaded() {
        assertNull("Services should not be loaded", bookService);
        assertNull("Controllers should not be loaded", booksController);
    }

    @Test
    public void findByIsbn() {
        // Populate the database
        final BookDocument book = new BookDocument();
        book.setIsbn("1234");
        book.setTitle("title");
        book.setYear(1990);
        book.setAuthors(Arrays.asList("a1", "a2"));
        mongoTemplate.save(book);

        {
            // Find
            final Optional<BookDocument> retrieved = repository.findByIsbn("1234");
            assertTrue("A book should be found", retrieved.isPresent());
            assertEquals(book, retrieved.get());
        }

        {
            // Not existing
            final Optional<BookDocument> retrieved = repository.findByIsbn("not_existing");
            assertFalse("No books should be found", retrieved.isPresent());
        }
    }

    @Test
    public void deleteByIsbn() {
        // Populate the database
        final BookDocument book = new BookDocument();
        book.setIsbn("1234");
        book.setTitle("title");
        book.setYear(1990);
        book.setAuthors(Arrays.asList("a1", "a2"));
        mongoTemplate.save(book);

        {
            // Find
            final Optional<BookDocument> retrieved = repository.findByIsbn("1234");
            assertTrue("A book should be found", retrieved.isPresent());
            assertEquals(book, retrieved.get());
        }

        repository.deleteByIsbn("1234");

        {
            // Not existing
            final Optional<BookDocument> retrieved = repository.findByIsbn("1234");
            assertFalse("No books should be found", retrieved.isPresent());
        }
    }
}
