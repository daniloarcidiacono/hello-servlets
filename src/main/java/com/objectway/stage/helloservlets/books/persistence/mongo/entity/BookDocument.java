package com.objectway.stage.helloservlets.books.persistence.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document("books")
public class BookDocument {
	@Id
	private String isbn;
	private String title;
	private Integer year;
	private List<String> authors;
	private String link;

	public String getIsbn() {
		return isbn;
	}

	public BookDocument setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public BookDocument setTitle(String title) {
		this.title = title;
		return this;
	}

	public Integer getYear() {
		return year;
	}

	public BookDocument setYear(Integer year) {
		this.year = year;
		return this;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public BookDocument setAuthors(List<String> authors) {
		this.authors = authors;
		return this;
	}

	public String getLink() {
		return link;
	}

	public BookDocument setLink(String link) {
		this.link = link;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookDocument book = (BookDocument) o;
		return Objects.equals(isbn, book.isbn) &&
				Objects.equals(title, book.title) &&
				Objects.equals(year, book.year) &&
				Objects.equals(authors, book.authors) &&
				Objects.equals(link, book.link);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn, title, year, authors, link);
	}
}
