package com.fernandocanabarro.desafio_fcamara_books.dtos;

import org.springframework.hateoas.RepresentationModel;

import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.projections.CopyProjection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CopyMinResponseDTO extends RepresentationModel<CopyMinResponseDTO>{

    private Long id;
    private Long bookId;
    private String title;
    private String author;
    private Integer pages;
    private String isbn;
    private Boolean available;

    public CopyMinResponseDTO(Copy entity){
        id = entity.getId();
        bookId = entity.getBook().getId();
        title = entity.getBook().getTitle();
        author = entity.getBook().getAuthor().getName();
        pages = entity.getBook().getPages();
        isbn = entity.getBook().getIsbn();
        available = entity.getAvailable();
    }

    public CopyMinResponseDTO(CopyProjection projection){
        id = projection.getId();
        bookId = projection.getBookId();
        title = projection.getTitle();
        author = projection.getAuthor();
        pages = projection.getPages();
        isbn = projection.getIsbn();
        available = projection.getAvailable();
    }
}
