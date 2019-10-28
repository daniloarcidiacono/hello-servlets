package com.objectway.stage.helloservlets.books.persistence.mysql.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class BookEntity {
	@Id
	@Column(name = "isbn", nullable = false, unique = true)
	private String isbn;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "year", nullable = false)
	private Integer year;

	@ManyToMany(cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	})
	@JoinTable(
		name = "books_authors",
		joinColumns = @JoinColumn(name = "book_id"),
		inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	@OrderBy("name")
	private Set<AuthorEntity> authors = new HashSet<>();

	@Column(name = "link")
	private String link;

	public String getIsbn() {
		return isbn;
	}

	public BookEntity setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public BookEntity setTitle(String title) {
		this.title = title;
		return this;
	}

	public Integer getYear() {
		return year;
	}

	public BookEntity setYear(Integer year) {
		this.year = year;
		return this;
	}

	public Set<AuthorEntity> getAuthors() {
		return authors;
	}

	public BookEntity setAuthors(Set<AuthorEntity> authors) {
		this.authors = authors;
		return this;
	}

	public String getLink() {
		return link;
	}

	public BookEntity setLink(String link) {
		this.link = link;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookEntity that = (BookEntity) o;
		return Objects.equals(isbn, that.isbn) &&
				Objects.equals(title, that.title) &&
				Objects.equals(year, that.year) &&
				Objects.equals(authors, that.authors) &&
				Objects.equals(link, that.link);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn, title, year, authors, link);
	}
}

