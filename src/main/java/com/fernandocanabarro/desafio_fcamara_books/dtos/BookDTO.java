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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO extends RepresentationModel<BookDTO>{

    private Long id;
    @NotBlank(message = "Campo Requerido")
    private String title;
    @NotBlank(message = "Campo Requerido")
    private String description;
    private AuthorDTO author;
    private Integer pages;
    @NotBlank(message = "Campo Requerido")
    private String isbn;
    @NotEmpty(message = "Livro deve ter ao menos uma Categoria")
    private List<CategoryDTO> categories = new ArrayList<>();

    public BookDTO(Book entity){
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        author = new AuthorDTO(entity.getAuthor());
        pages = entity.getPages();
        isbn = entity.getIsbn();
        for (Category x : entity.getCategories()){
            categories.add(new CategoryDTO(x));
        }
    }

}
