package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.dtos.BookDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAll(Pageable pageable){
        return null;
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id){
        return null;
    }

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAllByAuthor(Long id, Pageable pageable){
        return null;
    }

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAllByCategory(Long id, Pageable pageable){
        return null;
    }

    private boolean addCategories(Long id,BookMinDTO dto){
        return true;
    }

    @Transactional
    public BookDTO insert(BookDTO dto){
        return null;
    }  

    private void toEntity(Book book, BookDTO dto) {
        return;
    }

    @Transactional
    public BookDTO update(Long id, BookDTO dto){
        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id){
        return;
    }

}
