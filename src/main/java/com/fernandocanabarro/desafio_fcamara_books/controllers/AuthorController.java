package com.fernandocanabarro.desafio_fcamara_books.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AuthorDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;
import com.fernandocanabarro.desafio_fcamara_books.openapi.AuthorControllerOpenAPI;
import com.fernandocanabarro.desafio_fcamara_books.services.AuthorService;
import com.fernandocanabarro.desafio_fcamara_books.services.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements AuthorControllerOpenAPI{

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> findAll(Pageable pageable){
        Page<AuthorDTO> page = authorService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Page<BookMinDTO>> findAllBooksByAuthor(@PathVariable Long id, Pageable pageable){
        Page<BookMinDTO> page = bookService.findAllByAuthor(id, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AuthorDTO> insert(@RequestBody @Valid AuthorDTO dto){
        dto = authorService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
