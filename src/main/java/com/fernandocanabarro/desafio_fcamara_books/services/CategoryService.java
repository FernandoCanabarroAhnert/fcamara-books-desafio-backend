package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.controllers.CategoryController;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CategoryDTO;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable){
        return categoryRepository.findAll(pageable).map(x -> new CategoryDTO(x).add(linkTo(methodOn(CategoryController.class)
            .findAllBooksByCategory(x.getId(),null)).withRel("Consultar Livros da Categoria " + x.getName())));
    }
}
