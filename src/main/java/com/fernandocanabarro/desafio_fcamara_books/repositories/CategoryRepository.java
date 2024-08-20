package com.fernandocanabarro.desafio_fcamara_books.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.Category;
import com.fernandocanabarro.desafio_fcamara_books.projections.BookMinProjection;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

    @Query(nativeQuery = true,value = """
            SELECT b.id,b.title,a.name,b.pages,b.isbn
            FROM books AS b
            INNER JOIN authors AS a ON b.author_id = a.id
            INNER JOIN book_category AS bc ON bc.book_id = b.id
            INNER JOIN categories AS c ON bc.category_id = c.id
            WHERE c.id = :id
            """)
    Page<BookMinProjection> findProductsByCategory(Long id,Pageable pageable);

}
