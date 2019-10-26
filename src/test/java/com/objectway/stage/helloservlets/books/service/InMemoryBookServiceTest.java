package com.objectway.stage.helloservlets.books.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

public class InMemoryBookServiceTest {
	private final BookService service = new InMemoryBookService();

	@Test
	public void getAllBooks() {
//		service.getAllBooks()
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
