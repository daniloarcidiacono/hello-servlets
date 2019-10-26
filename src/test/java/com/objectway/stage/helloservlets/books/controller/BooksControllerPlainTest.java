package com.objectway.stage.helloservlets.books.controller;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.service.BookService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * You can write a plain unit test for a Spring MVC controller by using JUnit or TestNG.
 * To do so, instantiate the controller, inject it with mocked or stubbed dependencies, and call its methods
 * (passing MockHttpServletRequest, MockHttpServletResponse, and others, as necessary).
 * However, when writing such a unit test, much remains untested: for example, request mappings, data binding, type conversion,
 * validation, and much more.
 * Furthermore, other controller methods such as @InitBinder, @ModelAttribute, and @ExceptionHandler may also be invoked as part of
 * the request processing lifecycle.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BooksControllerPlainTest.Config.class)
public class BooksControllerPlainTest {
	private static final List<Book> stubBooks = Arrays.asList(
		new Book()
			.setTitle("title1")
			.setYear(1)
			.setAuthors(Arrays.asList("a1", "a2"))
			.setIsbn("1"),
		new Book()
			.setTitle("title2")
			.setYear(1)
			.setAuthors(Arrays.asList("a1", "a2"))
			.setIsbn("1")
			.setLink("link2")
	);

	private static final Book invalidBook = new Book();

	@Configuration
	public static class Config {
		@Bean
		public BookService bookService() {
			return Mockito.mock(BookService.class);
		}

		@Bean
		@Autowired
		public BooksController booksController(final BookService bookService) {
			return new BooksController(bookService);
		}
	}

	@Autowired
	private BookService bookService;

	@Autowired
	private BooksController booksController;

	@After
	public void resetMocks() {
		Mockito.reset(bookService);
	}

	@Test
	public void testGetAllBooks() {
		// Mockito returns an empty list by default
//		Mockito.when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
		assertEquals("No books should be returned", Collections.emptyList(), booksController.getAllBooks());

		Mockito.when(bookService.getAllBooks()).thenReturn(stubBooks);
		assertEquals("Stubbed books should be returned", stubBooks, booksController.getAllBooks());
	}

	@Test
	public void testSaveBook() {
		assertEquals("No books should be returned", Collections.emptyList(), booksController.getAllBooks());

		// The validation is not performed!
		booksController.saveBook(invalidBook);
	}
}
