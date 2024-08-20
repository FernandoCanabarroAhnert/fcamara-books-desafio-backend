package com.fernandocanabarro.desafio_fcamara_books.dtos;

import org.springframework.hateoas.RepresentationModel;

import com.fernandocanabarro.desafio_fcamara_books.entities.Author;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO extends RepresentationModel<AuthorDTO>{

    private Long id;
    @NotBlank(message = "Campo Requerido")
    private String name;

    public AuthorDTO(Author entity){
        id = entity.getId();
        name = entity.getName();
    }
}
