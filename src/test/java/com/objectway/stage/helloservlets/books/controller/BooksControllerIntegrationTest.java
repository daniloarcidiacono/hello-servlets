package com.objectway.stage.helloservlets.books.controller;

import com.objectway.stage.helloservlets.books.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * This integration tests runs an actual HTTP server.
 * <p>The whole stack (controller, service and persistence) is tested.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // It's important to NOT use WebEnvironment.MOCK (the default), otherwise you don’t load a real HTTP server
@ActiveProfiles("memory")
public class BooksControllerIntegrationTest {
//	private static final List<Book> stubBooks = Arrays.asList(
//		new Book()
//			.setTitle("title1")
//			.setYear(1)
//			.setAuthors(Arrays.asList("a1", "a2"))
//			.setIsbn("1"),
//		new Book()
//			.setTitle("title2")
//			.setYear(1)
//			.setAuthors(Arrays.asList("a1", "a2"))
//			.setIsbn("1")
//			.setLink("link2")
//	);
//
//	private static final Book invalidBook = new Book();

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllBooks() throws Exception {
		// No books should be returned
		final ResponseEntity<Book[]> response = restTemplate.getForEntity("/books", Book[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().length);

		// Stubbed books should be returned
//		Mockito.when(bookService.getAllBooks()).thenReturn(stubBooks);
//		mockMvc
//			.perform(
//				MockMvcRequestBuilders.get("/books")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON)
//			)
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//			.andExpect(status().isOk())
//			.andExpect(
//				ObjectMatcher.isEquals(
//					stubBooks,
//					new TypeReference<>() {}
//				)
//			);
	}

//	@Test
//	public void testSaveBook() throws Exception {
//		// The validation is performed!
//		mockMvc.perform(
//				MockMvcRequestBuilders.put(
//						"/books"
//				)
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON)
//						.content(asJsonString(stubBooks.get(0)))
//		)
//				.andExpect(status().isNoContent());
//	}
//
//	@Test
//	public void testSaveInvalidBook() throws Exception {
//		// The validation is performed!
//		mockMvc.perform(
//			MockMvcRequestBuilders.put(
//				"/books"
//			)
//			.contentType(MediaType.APPLICATION_JSON)
//			.accept(MediaType.APPLICATION_JSON)
//			.content(asJsonString(invalidBook))
//		)
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//		.andExpect(status().isBadRequest())
//		.andExpect(
//			ObjectMatcher.isEquals(
//				new ValidationErrorDTO(
//					"Validation error",
//					Arrays.asList(
//						Pair.of("year", "must not be null"),
//						Pair.of("isbn", "must not be null"),
//						Pair.of("title", "must not be null"),
//						Pair.of("authors", "must not be empty")
//					)
//				),
//				new TypeReference<>() {}
//			)
//		);
//	}
}
