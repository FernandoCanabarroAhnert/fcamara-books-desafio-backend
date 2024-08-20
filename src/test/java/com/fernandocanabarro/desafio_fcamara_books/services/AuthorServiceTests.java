package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AuthorDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Author;
import com.fernandocanabarro.desafio_fcamara_books.factories.AuthorFactory;
import com.fernandocanabarro.desafio_fcamara_books.repositories.AuthorRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ExistingResource;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private Author author;
    private AuthorDTO dto;

    @BeforeEach
    public void setup() throws Exception{
        author = AuthorFactory.createAuthor();
        dto = new AuthorDTO(null, "George Orwell");
    }
    
    @Test
    public void findAllShouldReturnPageOfAuthorDTO(){
        Page<Author> page = new PageImpl<>(List.of(new Author(1L,"George Orwell")));
        Pageable pageable = PageRequest.of(0, 10);
        when(authorRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<AuthorDTO> response = authorService.findAll(pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getName()).isEqualTo("George Orwell");
    }

    @Test
    public void insertShouldReturnAuthorDTOWhenDataIsValid(){
        when(authorRepository.findByName("George Orwell")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO response = authorService.insert(dto);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("George Orwell");
    }

    
    @Test
    public void insertShouldThrowExistingResourceWhenAuthorAlreadyExists(){
        when(authorRepository.findByName("George Orwell")).thenReturn(Optional.of(author));

        assertThatThrownBy(() -> authorService.insert(dto)).isInstanceOf(ExistingResource.class);
    }
}
