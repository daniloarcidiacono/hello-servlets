package com.objectway.stage.helloservlets.books.persistence.mysql.repository;

import com.objectway.stage.helloservlets.books.persistence.mysql.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<BookEntity, Long> {
	Optional<BookEntity> findByIsbn(final String isbn);
	void deleteByIsbn(final String isbn);
}
