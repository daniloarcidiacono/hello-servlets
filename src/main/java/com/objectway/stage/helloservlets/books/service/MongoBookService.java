package com.objectway.stage.helloservlets.books.service;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.persistence.mongo.entity.BookDocument;
import com.objectway.stage.helloservlets.books.persistence.mongo.mapper.BookDocumentMapper;
import com.objectway.stage.helloservlets.books.persistence.mongo.repository.BooksMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("mongodb")
@Primary
// Generally speaking, transactions require at least MongoDB 3.7.9 (development build) running in a replica set. Sharded cluster is not supported.
//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
public class MongoBookService implements BookService {
	private final BooksMongoRepository booksRepository;

	@Autowired
	public MongoBookService(final BooksMongoRepository booksRepository) {
		this.booksRepository = booksRepository;
	}

	@Override
	public List<Book> getAllBooks() {
		return booksRepository.findAll().stream().map(BookDocumentMapper::toModel).collect(Collectors.toList());
	}

	@Override
	public Optional<Book> searchBookByISBN(final String isbn) {
		return booksRepository.findByIsbn(isbn).map(BookDocumentMapper::toModel);
	}

	@Override
	public void saveBook(final Book book) {
		// Create the entity to save
		final BookDocument bookEntity = BookDocumentMapper.toEntity(book);

		// Save the book (the new authors will be saved due to cascade)
		booksRepository.save(bookEntity);
	}

	@Override
	public void deleteBookByISBN(final String isbn) {
		booksRepository.deleteByIsbn(isbn);
	}
}
