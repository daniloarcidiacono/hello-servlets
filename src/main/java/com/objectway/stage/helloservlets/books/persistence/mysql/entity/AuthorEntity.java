package com.objectway.stage.helloservlets.books.persistence.mysql.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class AuthorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public Long getId() {
		return id;
	}

	public AuthorEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public AuthorEntity setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuthorEntity that = (AuthorEntity) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}

