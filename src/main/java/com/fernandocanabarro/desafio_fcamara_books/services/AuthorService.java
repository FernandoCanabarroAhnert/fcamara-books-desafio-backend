package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AuthorDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable){
        return null;
    }

    @Transactional
    public AuthorDTO insert(AuthorDTO dto){
        return null;
    }
}
