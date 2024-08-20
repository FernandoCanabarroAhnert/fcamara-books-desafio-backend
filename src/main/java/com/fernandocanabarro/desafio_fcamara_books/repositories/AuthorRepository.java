package com.fernandocanabarro.desafio_fcamara_books.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>{

    Optional<Author> findByName(String name);
}
