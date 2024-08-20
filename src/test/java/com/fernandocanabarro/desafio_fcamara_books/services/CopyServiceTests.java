package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyMinResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;
import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.factories.BookFactory;
import com.fernandocanabarro.desafio_fcamara_books.factories.CopyFactory;
import com.fernandocanabarro.desafio_fcamara_books.projections.CopyProjection;
import com.fernandocanabarro.desafio_fcamara_books.repositories.BookRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CopyRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CopyServiceTests {

    @InjectMocks
    private CopyService copyService;
    @Mock
    private CopyRepository copyRepository;
    @Mock
    private BookRepository bookRepository;

    private Book book;
    private Copy copy;
    private CopyProjection copyProjection;
    private CopyRequestDTO copyRequestDTO;

    @BeforeEach
    public void setup() throws Exception{
        book = BookFactory.createBook();
        copy = CopyFactory.createCopy();
        copyProjection = new CopyProjection() {
            @Override
            public Long getId(){
                return 1L;
            }
            @Override
            public Long getBookId(){
                return 1L;
            }
            @Override
            public String getTitle(){
                return "book";
            }
            @Override
            public String getAuthor(){
                return "George Orwell";
            }
            @Override
            public Integer getPages(){
                return 100;
            }
            @Override
            public String getIsbn(){
                return "123456";
            }
            @Override
            public Boolean getAvailable(){
                return true;
            }
            
        };

        copyRequestDTO = new CopyRequestDTO(1L);
    }

    @Test
    public void findAllAvailableCopiesShouldReturnPageOfCopyMinResponseDTO(){
        Page<CopyProjection> page = new PageImpl<>(List.of(copyProjection));
        Pageable pageable = PageRequest.of(0, 10);

        when(copyRepository.findAllCopiesAvailable(PageRequest.of(0, 10))).thenReturn(page);

        Page<CopyMinResponseDTO> response = copyService.findAllCopiesAvailable(pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getBookId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
    }

     @Test
    public void insertShouldReturnCopyMinResponseDTOWhenDataIsValid(){
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(copyRepository.save(any(Copy.class))).thenReturn(copy);

        CopyMinResponseDTO response = copyService.insert(copyRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getBookId()).isEqualTo(1L);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("book");
    }

    
    @Test
    public void insertShouldThrowExistingResourceWhenAuthorAlreadyExists(){
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> copyService.insert(copyRequestDTO)).isInstanceOf(ResourceNotFoundException.class);
    }
}
