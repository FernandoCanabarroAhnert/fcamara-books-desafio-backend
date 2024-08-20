package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.dtos.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {


    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable){
        return null;
    }
}
