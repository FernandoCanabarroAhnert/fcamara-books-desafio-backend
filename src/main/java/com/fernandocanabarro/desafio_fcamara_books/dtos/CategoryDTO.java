package com.fernandocanabarro.desafio_fcamara_books.dtos;

import org.springframework.hateoas.RepresentationModel;

import com.fernandocanabarro.desafio_fcamara_books.entities.Category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO extends RepresentationModel<CategoryDTO>{

    @NotNull(message = "Campo Requerido")
    private Long id;
    private String name;

    public CategoryDTO(Category entity){
        id = entity.getId();
        name = entity.getName();
    }
}
