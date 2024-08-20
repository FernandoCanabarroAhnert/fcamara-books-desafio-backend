package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.controllers.AuthorController;
import com.fernandocanabarro.desafio_fcamara_books.dtos.AuthorDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Author;
import com.fernandocanabarro.desafio_fcamara_books.repositories.AuthorRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ExistingResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable){
        return authorRepository.findAll(pageable).map(x -> new AuthorDTO(x).add(linkTo(methodOn(AuthorController.class)
                .findAllBooksByAuthor(x.getId(), null)).withRel("Consultar Livros do Autor " + x.getName())));
    }

    @Transactional
    public AuthorDTO insert(AuthorDTO dto){
        Optional<Author> existingAuthor = authorRepository.findByName(dto.getName());
        if (existingAuthor.isPresent()) {
            throw new ExistingResource("Este Autor já Existe");
        }
        Author author = new Author();
        author.setName(dto.getName());
        author = authorRepository.save(author);
        return new AuthorDTO(author.getId(),author.getName());
    }
}
