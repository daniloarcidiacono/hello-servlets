package com.objectway.stage.helloservlets.books.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * POJO for a book.
 */
public class Book {
	@NotNull
	private String isbn;

	@NotNull
	private String title;

	@NotNull
	private Integer year;

	@NotEmpty
	private List<String> authors;

	private String link;

	public String getIsbn() {
		return isbn;
	}

	public Book setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Book setTitle(String title) {
		this.title = title;
		return this;
	}

	public Integer getYear() {
		return year;
	}

	public Book setYear(Integer year) {
		this.year = year;
		return this;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public Book setAuthors(List<String> authors) {
		this.authors = authors;
		return this;
	}

	public String getLink() {
		return link;
	}

	public Book setLink(String link) {
		this.link = link;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
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

	@Override
	public String toString() {
		return "Book{" +
				"isbn='" + isbn + '\'' +
				", title='" + title + '\'' +
				", year=" + year +
				", authors=" + authors +
				", link='" + link + '\'' +
				'}';
	}
}
