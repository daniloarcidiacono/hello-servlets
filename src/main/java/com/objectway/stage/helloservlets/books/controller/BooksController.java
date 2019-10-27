package com.objectway.stage.helloservlets.books.controller;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * &#64;RestController is a composed annotation that is itself meta-annotated
 * with @Controller and @ResponseBody to indicate a controller whose every
 * method inherits the type-level @ResponseBody annotation and,
 * therefore, writes directly to the response body versus view resolution and
 * rendering with an HTML template.
 */
@RestController
@RequestMapping("/books")
public class BooksController {
	private final BookService bookService;

	@Autowired
	public BooksController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("throwError")
	public void throwError() {
		throw new NullPointerException("This is designed to throw an exception!");
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping(params = "isbn")
	public ResponseEntity<Book> searchBookByISBN(@RequestParam final String isbn) {
		final Optional<Book> book = bookService.searchBookByISBN(isbn);
		if (book.isPresent()) {
			return ResponseEntity.ok(book.get());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void saveBook(@Valid @RequestBody final Book book) {
		bookService.saveBook(book);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBookByISBN(@RequestParam final String isbn) {
		bookService.deleteBookByISBN(isbn);
	}
}
