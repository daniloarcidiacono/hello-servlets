package com.objectway.stage.helloservlets.books.service;

import com.objectway.stage.helloservlets.books.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link BookService} that stores data in memory.
 */
@Service
public class InMemoryBookService implements BookService {
	private List<Book> books = new ArrayList<>();

	@Override
	public List<Book> getAllBooks() {
		return Collections.unmodifiableList(books);
	}

	@Override
	public Optional<Book> searchBookByISBN(final String isbn) {
		if (isbn == null) {
			throw new IllegalArgumentException("ISBN code cannot be null");
		}

		return books.stream()
			.filter(book -> isbn.equals(book.getIsbn()))
			.findFirst();
	}

	@Override
	public void saveBook(final Book book) {
		if (book == null) {
			throw new IllegalArgumentException("book cannot be null");
		}

		books.add(book);
	}

	@Override
	public void deleteBookByISBN(final String isbn) {
		if (isbn == null) {
			throw new IllegalArgumentException("ISBN code cannot be null");
		}

		books.removeIf(x -> isbn.equals(x.getIsbn()));
	}
}
