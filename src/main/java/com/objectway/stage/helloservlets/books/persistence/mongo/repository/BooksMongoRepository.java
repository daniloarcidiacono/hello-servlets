package com.objectway.stage.helloservlets.books.persistence.mongo.repository;

import com.objectway.stage.helloservlets.books.persistence.mongo.entity.BookDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksMongoRepository extends MongoRepository<BookDocument, String> {
	Optional<BookDocument> findByIsbn(final String isbn);
	void deleteByIsbn(final String isbn);
}
