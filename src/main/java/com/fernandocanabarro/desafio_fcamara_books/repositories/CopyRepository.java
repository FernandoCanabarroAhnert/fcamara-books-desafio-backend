package com.fernandocanabarro.desafio_fcamara_books.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.projections.CopyProjection;

@Repository
public interface CopyRepository extends JpaRepository<Copy,Long>{

    @Query(nativeQuery = true, value = 
    """
        SELECT c.id,b.id AS bookId,b.title,a.name AS author,b.pages, b.isbn, c.available
        FROM copies AS c
        INNER JOIN books AS b ON c.book_id = b.id
        INNER JOIN authors AS a ON b.author_id = a.id
        WHERE c.available = 1
            """)
    Page<CopyProjection> findAllCopiesAvailable(Pageable pageable);
}
