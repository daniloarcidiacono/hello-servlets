package com.objectway.stage.helloservlets.books.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.objectway.stage.helloservlets.books.dto.ErrorDTO;
import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Unit test for {@link BooksServlet}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BooksServletTest {
	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private BookService bookService;

	private final ObjectMapper mapper = new ObjectMapper();

	// Test data
	private final List<Book> mockBookList = Arrays.asList(
		new Book()
			.setIsbn("isbn1")
			.setTitle("title1")
			.setYear(1)
			.setAuthors(Arrays.asList("author1", "author2"))
			.setLink("link1"),
		new Book()
			.setIsbn("isbn2")
			.setTitle("title2")
			.setYear(2)
			.setAuthors(Collections.singletonList("author2"))
	);

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllBooks() throws Exception {
		// Prepare mocks
		Mockito.when(request.getServletPath()).thenReturn("/books");
		Mockito.when(request.getParameter("isbn")).thenReturn(null);

		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

		Mockito.when(bookService.getAllBooks()).thenReturn(mockBookList);

		// Perform the request
		new BooksServlet(bookService).doGet(request, response);

		// Parse
		final String result = sw.getBuffer().toString().trim();
		final List<Book> retrievedBooks = mapper.readValue(
			result,
			new TypeReference<List<Book>>() { }
		);

		// Assert
//		assertEquals("Should return 200 OK", 200, response.getStatus());
		assertEquals("Should return all books", mockBookList, retrievedBooks);
	}

	@Test
	public void testSearchBookByISBN() throws Exception {
		final Book book = mockBookList.get(0);
		final String isbn = book.getIsbn();

		// Prepare mocks
		Mockito.when(request.getServletPath()).thenReturn("/books");
		Mockito.when(request.getParameter("isbn")).thenReturn(isbn);

		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

		Mockito.when(bookService.searchBookByISBN(isbn)).thenReturn(Optional.of(book));

		assertNotNull("Mockito is working", bookService.searchBookByISBN(isbn));

		// Perform the request
		new BooksServlet(bookService).doGet(request, response);

		// Parse
		final String result = sw.getBuffer().toString().trim();
		final Book retrievedBook = mapper.readValue(result, Book.class);

		// Assert
//		Mockito.verify(response).setStatus(200);
		assertEquals("Should return the first book", book, retrievedBook);
	}

	@Test
	public void testSearchNotExistingBookByISBN() throws Exception {
		final String isbn = "not_existing";

		// Prepare mocks
		Mockito.when(request.getServletPath()).thenReturn("/books");
		Mockito.when(request.getParameter("isbn")).thenReturn(isbn);

		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);

		Mockito.when(bookService.searchBookByISBN(isbn)).thenReturn(Optional.empty());

		// Perform the request
		new BooksServlet(bookService).doGet(request, response);

		// Parse
		final String result = sw.getBuffer().toString().trim();
		final ErrorDTO bookNotFoundError = mapper.readValue(result, ErrorDTO.class);

		// Assert
		Mockito.verify(response).setStatus(404);
		assertEquals("Should return an error message", "Book not found", bookNotFoundError.getMessage());
	}
}
