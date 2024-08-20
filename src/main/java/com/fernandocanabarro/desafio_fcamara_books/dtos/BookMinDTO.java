package com.fernandocanabarro.desafio_fcamara_books.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fernandocanabarro.desafio_fcamara_books.entities.Book;
import com.fernandocanabarro.desafio_fcamara_books.entities.Category;
import com.fernandocanabarro.desafio_fcamara_books.projections.BookMinProjection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookMinDTO extends RepresentationModel<BookMinDTO>{

    private Long id;
    private String title;
    private String author;
    private Integer pages;
    private String isbn;
    private List<CategoryDTO> categories = new ArrayList<>();

    public BookMinDTO(Book entity){
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor().getName();
        pages = entity.getPages();
        isbn = entity.getIsbn();
        for (Category x : entity.getCategories()){
            categories.add(new CategoryDTO(x));
        }
    }

    public BookMinDTO(BookMinProjection projection){
        id = projection.getId();
        title = projection.getTitle();
        author = projection.getName();
        pages = projection.getPages();
        isbn = projection.getIsbn();
    }

}
