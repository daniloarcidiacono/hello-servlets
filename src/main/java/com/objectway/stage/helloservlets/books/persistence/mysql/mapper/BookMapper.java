package com.objectway.stage.helloservlets.books.persistence.mysql.mapper;

import com.objectway.stage.helloservlets.books.model.Book;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.AuthorEntity;
import com.objectway.stage.helloservlets.books.persistence.mysql.entity.BookEntity;

import java.util.stream.Collectors;

/**
 * Mapper between {@link Book} and {@link BookEntity}.
 */
public class BookMapper {
	private BookMapper() {
	}

	public static Book toModel(final BookEntity entity) {
		return new Book()
			.setIsbn(entity.getIsbn())
			.setTitle(entity.getTitle())
			.setYear(entity.getYear())
			.setLink(entity.getLink())
			.setAuthors(entity.getAuthors().stream().map(AuthorEntity::getName).collect(Collectors.toList()));
	}

	public static BookEntity toEntity(final Book model) {
		return new BookEntity()
			.setIsbn(model.getIsbn())
			.setTitle(model.getTitle())
			.setYear(model.getYear())
			.setLink(model.getLink());
	}
}
