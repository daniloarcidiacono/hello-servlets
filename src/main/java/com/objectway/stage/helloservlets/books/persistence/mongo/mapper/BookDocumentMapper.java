package com.objectway.stage.helloservlets.books.persistence.mongo.mapper;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.persistence.mongo.entity.BookDocument;

/**
 * Mapper between {@link Book} and {@link BookDocument}.
 */
public class BookDocumentMapper {
	private BookDocumentMapper() {
	}

	public static Book toModel(final BookDocument entity) {
		return new Book()
			.setIsbn(entity.getIsbn())
			.setTitle(entity.getTitle())
			.setYear(entity.getYear())
			.setLink(entity.getLink())
			.setAuthors(entity.getAuthors());
	}

	public static BookDocument toEntity(final Book model) {
		return new BookDocument()
			.setIsbn(model.getIsbn())
			.setTitle(model.getTitle())
			.setYear(model.getYear())
			.setLink(model.getLink())
			.setAuthors(model.getAuthors());
	}
}
