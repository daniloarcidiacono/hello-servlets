package com.objectway.stage.helloservlets.books.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.objectway.stage.helloservlets.api.advice.HelloServletsControllerAdvice;
import com.objectway.stage.helloservlets.books.dto.ValidationErrorDTO;
import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.service.BookService;
import com.objectway.stage.helloservlets.test.utils.MatcherUtils;
import com.objectway.stage.helloservlets.test.utils.ObjectMatcher;
import com.objectway.stage.helloservlets.utils.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.objectway.stage.helloservlets.test.utils.MatcherUtils.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * &#64;WebAppConfiguration is a class-level annotation that you can use to declare that the
 * ApplicationContext loaded for an integration test should be a WebApplicationContext.
 * The mere presence of @WebAppConfiguration on a test class ensures that a WebApplicationContext is loaded
 * for the test, using the default value of "file:src/main/webapp" for the path to the root of the web application
 * (that is, the resource base path).
 *
 * Note that @WebAppConfiguration must be used in conjunction with @ContextConfiguration.
 *
 * The dependencies of your test instances are injected from beans in the application context
 * that you configured with @ContextConfiguration or related annotations.
 * You may use setter injection, field injection, or both, depending on which annotations
 * you choose and whether you place them on setter methods or fields.
 *
 * The Spring MVC Test framework provides first class support for testing Spring MVC code with a fluent API
 * that you can use with JUnit, TestNG, or any other testing framework. It is built on the Servlet API mock
 * objects from the spring-test module and, hence, does not use a running Servlet container.
 * It uses the DispatcherServlet to provide full Spring MVC runtime behavior and provides support for loading
 * actual Spring configuration with the TestContext framework in addition to a standalone mode, in which you
 * can manually instantiate controllers and test them one at a time.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BooksControllerTest.Config.class) // locations = "classpath:app-context.xml")
@WebAppConfiguration
public class BooksControllerTest {
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

		@Bean
		public HelloServletsControllerAdvice helloServletsControllerAdvice() {
			return new HelloServletsControllerAdvice();
		}
	}

	@Autowired
	private HelloServletsControllerAdvice helloServletsControllerAdvice;

	@Autowired
	private BookService bookService;

	@Autowired
	private BooksController booksController;
	private MockMvc mockMvc;

	@Before
	public void setupMockMvc() {
		assertNotNull(booksController);
		assertNotNull(helloServletsControllerAdvice);

		this.mockMvc = MockMvcBuilders
			.standaloneSetup(booksController)
			.setControllerAdvice(helloServletsControllerAdvice)
			.build();
	}

	@After
	public void resetMocks() {
		Mockito.reset(bookService);
	}

	@Test
	public void testGetAllBooks() throws Exception {
		// Mockito returns an empty list by default
//		Mockito.when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

		// No books should be returned
		mockMvc
			.perform(
				MockMvcRequestBuilders.get("/books")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));

		// Stubbed books should be returned
		Mockito.when(bookService.getAllBooks()).thenReturn(stubBooks);
		mockMvc
			.perform(
				MockMvcRequestBuilders.get("/books")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(
				ObjectMatcher.isEquals(
					stubBooks,
					new TypeReference<>() {}
				)
			);
	}

	@Test
	public void testSaveBook() throws Exception {
		// The validation is performed!
		mockMvc.perform(
				MockMvcRequestBuilders.put(
						"/books"
				)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(asJsonString(stubBooks.get(0)))
		)
				.andExpect(status().isNoContent());
	}

	@Test
	public void testSaveInvalidBook() throws Exception {
		// The validation is performed!
		mockMvc.perform(
			MockMvcRequestBuilders.put(
				"/books"
			)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(asJsonString(invalidBook))
		)
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest())
		.andExpect(
			ObjectMatcher.isEquals(
				new ValidationErrorDTO(
					"Validation error",
					Arrays.asList(
						Pair.of("year", "must not be null"),
						Pair.of("isbn", "must not be null"),
						Pair.of("title", "must not be null"),
						Pair.of("authors", "must not be empty")
					)
				),
				new TypeReference<>() {}
			)
		);
	}
}
