package com.fernandocanabarro.desafio_fcamara_books.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CategoryDTO;
import com.fernandocanabarro.desafio_fcamara_books.services.BookService;
import com.fernandocanabarro.desafio_fcamara_books.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @GetMapping("/{id}/books")
    public ResponseEntity<Page<BookMinDTO>> findAllBooksByCategory(@PathVariable Long id, Pageable pageable){
        Page<BookMinDTO> page = bookService.findAllByCategory(id, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable){
        Page<CategoryDTO> page = categoryService.findAll(pageable);
        return ResponseEntity.ok(page);
    }
}
