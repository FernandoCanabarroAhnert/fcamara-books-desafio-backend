package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyMinResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CopyService {

    @Transactional
    public Page<CopyMinResponseDTO> findAllCopiesAvailable(Pageable pageable){
        return null;
    }

    @Transactional
    public CopyMinResponseDTO insert(CopyRequestDTO dto){
       return null;
    }
}
