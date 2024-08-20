package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.controllers.AuthorController;
import com.fernandocanabarro.desafio_fcamara_books.controllers.BookController;
import com.fernandocanabarro.desafio_fcamara_books.controllers.CategoryController;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CategoryDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Author;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;
import com.fernandocanabarro.desafio_fcamara_books.entities.Category;
import com.fernandocanabarro.desafio_fcamara_books.repositories.AuthorRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.BookRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CategoryRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAll(Pageable pageable){
        Page<BookMinDTO> page = bookRepository.searchBooksWithCategories(pageable).map(x -> new BookMinDTO(x).add(linkTo(methodOn(BookController.class)
                    .findById(x.getId())).withRel("Consultar Livro por Id")));
        for (BookMinDTO x : page.getContent()){
            for (CategoryDTO cat : x.getCategories()){
                cat.add(linkTo(methodOn(CategoryController.class).findAllBooksByCategory(x.getId(),null))
                .withRel("Consultar Livros da Categoria " + cat.getName()));
            }
        }
        return page;
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id){
        Book obj = bookRepository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException(id));
        BookDTO dto = new BookDTO(obj);
        dto.getAuthor().add(linkTo(methodOn(AuthorController.class)
            .findAllBooksByAuthor(obj.getAuthor().getId(), null)).withRel("Consultar Livros do Autor " + dto.getAuthor().getName()));
        for (CategoryDTO x : dto.getCategories()){
            x.add(linkTo(methodOn(CategoryController.class).findAllBooksByCategory(x.getId(),null))
                .withRel("Consultar Livros da Categoria " + x.getName()));
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAllByAuthor(Long id, Pageable pageable){
        Author author = authorRepository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException(id));
        Page<BookMinDTO> page =  bookRepository.findAllByAuthor(pageable,author).map(x -> new BookMinDTO(x).add(linkTo(methodOn(BookController.class)
            .findById(x.getId())).withRel("Consultar Livro por Id")));
        for (BookMinDTO x : page.getContent()){
            for (CategoryDTO cat : x.getCategories()){
                cat.add(linkTo(methodOn(CategoryController.class).findAllBooksByCategory(x.getId(),null))
                .withRel("Consultar Livros da Categoria " + cat.getName()));
            }
        }
        return page;
    }

    @Transactional(readOnly = true)
    public Page<BookMinDTO> findAllByCategory(Long id, Pageable pageable){
        Category category = categoryRepository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException(id));
        Page<BookMinDTO> page = categoryRepository.findProductsByCategory(category.getId(), pageable).map(x -> new BookMinDTO(x)
                .add(linkTo(methodOn(BookController.class)
                .findById(x.getId())).withRel("Consultar Livro por Id")));
        page.map(x -> addCategories(x.getId(), x));
        return page;
    }

    private boolean addCategories(Long id,BookMinDTO dto){
        BookDTO book = this.findById(id);
        return dto.getCategories().addAll(book.getCategories());
    }

    @Transactional
    public BookDTO insert(BookDTO dto){
        Book book = new Book();
        toEntity(book,dto);
        book = bookRepository.save(book);
        BookDTO response = new BookDTO(book);
        response.getAuthor().add(linkTo(methodOn(AuthorController.class)
            .findAllBooksByAuthor(response.getAuthor().getId(), null)).withRel("Consultar Livros do Autor " + response.getAuthor().getName()));
        for (CategoryDTO x : response.getCategories()){
            x.add(linkTo(methodOn(CategoryController.class).findAllBooksByCategory(x.getId(),null))
                .withRel("Consultar Livros da Categoria " + x.getName()));
        }
        return response.add(linkTo(methodOn(BookController.class)
            .findById(response.getId())).withRel("Consultar Livro por Id"));
    }  

    private void toEntity(Book book, BookDTO dto) {
        book.getCategories().clear();
        book.setTitle(dto.getTitle());
        book.setAuthor(authorRepository.findById(dto.getAuthor().getId()).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado")));
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.getCategories().clear();
        for (CategoryDTO x : dto.getCategories()){
            Category category = categoryRepository.findById(x.getId()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            book.getCategories().add(category);
        }
        book.setIsbn(dto.getIsbn());
    }

    @Transactional
    public BookDTO update(Long id, BookDTO dto){
        try{
            Book book = bookRepository.getReferenceById(id);
            toEntity(book, dto);
            book = bookRepository.save(book);
            BookDTO response = new BookDTO(book);
            response.getAuthor().add(linkTo(methodOn(AuthorController.class)
                .findAllBooksByAuthor(response.getAuthor().getId(), null)).withRel("Consultar Livros do Autor " + response.getAuthor().getName()));
            for (CategoryDTO x : response.getCategories()){
                x.add(linkTo(methodOn(CategoryController.class).findAllBooksByCategory(x.getId(),null))
                    .withRel("Consultar Livros da Categoria " + x.getName()));
        }
        return response.add(linkTo(methodOn(BookController.class)
            .findById(response.getId())).withRel("Consultar Livro por Id"));
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Livro não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id){
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
            bookRepository.deleteById(id);
    }

}
