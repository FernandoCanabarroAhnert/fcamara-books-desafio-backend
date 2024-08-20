package com.fernandocanabarro.desafio_fcamara_books.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.Author;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{

    @Query(value = "SELECT obj FROM Book obj JOIN FETCH obj.categories",
        countQuery = "SELECT COUNT(obj) FROM Book obj JOIN obj.categories")
    Page<Book> searchBooksWithCategories(Pageable pageable);

    Page<Book> findAllByAuthor(Pageable pageable,Author author);
}
