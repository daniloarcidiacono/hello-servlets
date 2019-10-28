package com.objectway.stage.helloservlets.books.service;

import com.objectway.stage.helloservlets.books.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Interface for handling {@link Book}s.
 * @see Book
 */
public interface BookService {
	/**
	 * Retrieves every book.
	 * @return the list of books, or an empty list if there is none.
	 */
	List<Book> getAllBooks();

	/**
	 * Searches for a book having the given ISBN code.
	 *
	 * @param isbn the isbn to search for (cannot be null)
	 * @return an optional book instance
	 * @throws IllegalArgumentException if isbn is null
	 */
	Optional<Book> searchBookByISBN(final String isbn);

	/**
	 * Saves or updates a book.
	 * @param book the book to save (cannot be null)
	 * @throws IllegalArgumentException if book is null
	 */
	void saveBook(final Book book);

	/**
	 * Deletes the book having the given ISBN.
	 * @param isbn the isbn to search for (cannot be null)
	 * @throws IllegalArgumentException if isbn is null
	 */
	void deleteBookByISBN(final String isbn);
}
