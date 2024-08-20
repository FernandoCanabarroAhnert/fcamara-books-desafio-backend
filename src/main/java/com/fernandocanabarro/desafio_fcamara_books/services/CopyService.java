package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.controllers.BookController;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyMinResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;
import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.repositories.BookRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CopyRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CopyService {

    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Page<CopyMinResponseDTO> findAllCopiesAvailable(Pageable pageable){
        Page<CopyMinResponseDTO> page =  copyRepository.findAllCopiesAvailable(pageable).map(x -> new CopyMinResponseDTO(x)
            .add(linkTo(methodOn(BookController.class).findById(x.getBookId())).withRel("Consultar Livro por Id")));
        return page;
    }

    @Transactional
    public CopyMinResponseDTO insert(CopyRequestDTO dto){
        Copy copy = new Copy();
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(() -> 
            new ResourceNotFoundException(dto.getBookId()));
        copy.setBook(book);
        copy.setAvailable(true);
        copy = copyRepository.save(copy);
        return new CopyMinResponseDTO(copy).add(linkTo(methodOn(BookController.class)
            .findById(copy.getBook().getId())).withRel("Consultar Livro por Id"));
    }
}
