package com.objectway.stage.helloservlets.books.persistence.mysql.repository;

import com.objectway.stage.helloservlets.books.persistence.mysql.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorsRepository extends JpaRepository<AuthorEntity, Long> {
	List<AuthorEntity> findByNameIn(final List<String> name);
}
