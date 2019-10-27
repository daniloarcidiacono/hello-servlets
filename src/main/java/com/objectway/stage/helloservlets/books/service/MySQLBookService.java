package com.objectway.stage.helloservlets.books.service;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.AuthorEntity;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.BookEntity;
import com.objectway.stage.helloservlets.books.persistence.mysql.mapper.BookMapper;
import com.objectway.stage.helloservlets.books.persistence.mysql.repository.AuthorsRepository;
import com.objectway.stage.helloservlets.books.persistence.mysql.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Profile("mysql")
@Primary
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
public class MySQLBookService implements BookService {
	private final BooksRepository booksRepository;
	private final AuthorsRepository authorsRepository;

	@Autowired
	public MySQLBookService(final BooksRepository booksRepository, final AuthorsRepository authorsRepository) {
		this.booksRepository = booksRepository;
		this.authorsRepository = authorsRepository;
	}

	@Override
	public List<Book> getAllBooks() {
		return booksRepository.findAll().stream().map(BookMapper::toModel).collect(Collectors.toList());
	}

	@Override
	public Optional<Book> searchBookByISBN(final String isbn) {
		return booksRepository.findByIsbn(isbn).map(BookMapper::toModel);
	}

	@Override
	public void saveBook(final Book book) {
		// Fetch existing authors
		final Map<String, AuthorEntity> existingAuthors = authorsRepository.findByNameIn(book.getAuthors())
			.stream()
			.collect(
				Collectors.toMap(
					AuthorEntity::getName,
					Function.identity()
				)
			);

		// Create the entity to save
		final BookEntity bookEntity = BookMapper.toEntity(book);

		// For each author of the book to save
		for (String name : book.getAuthors()) {
			// If it does not exist in the database
			if (!existingAuthors.containsKey(name)) {
				// Create a new entity
				existingAuthors.put(name, new AuthorEntity().setName(name));
			}

			// Add the author entity to the book entity
			bookEntity.getAuthors().add(existingAuthors.get(name));
		}

		// Save the book (the new authors will be saved due to cascade)
		booksRepository.save(bookEntity);
	}

	@Override
	public void deleteBookByISBN(final String isbn) {
		booksRepository.deleteByIsbn(isbn);
	}
}
