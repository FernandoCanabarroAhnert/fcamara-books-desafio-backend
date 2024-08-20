package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fernandocanabarro.desafio_fcamara_books.dtos.CategoryDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Category;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CategoryRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void findAllShouldReturnPageOfCategoryDTO(){
        Page<Category> page = new PageImpl<>(List.of(new Category(1L,"Fiction")));
        Pageable pageable = PageRequest.of(0, 10);
        when(categoryRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<CategoryDTO> response = categoryService.findAll(pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getName()).isEqualTo("Fiction");
    }
}
