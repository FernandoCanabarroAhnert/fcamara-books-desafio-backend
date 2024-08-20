package com.fernandocanabarro.desafio_fcamara_books.services;

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

import com.fernandocanabarro.desafio_fcamara_books.dtos.BookDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Author;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;
import com.fernandocanabarro.desafio_fcamara_books.entities.Category;
import com.fernandocanabarro.desafio_fcamara_books.factories.AuthorFactory;
import com.fernandocanabarro.desafio_fcamara_books.factories.BookFactory;
import com.fernandocanabarro.desafio_fcamara_books.factories.CategoryFactory;
import com.fernandocanabarro.desafio_fcamara_books.projections.BookMinProjection;
import com.fernandocanabarro.desafio_fcamara_books.repositories.AuthorRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.BookRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CategoryRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private Long existingId,nonExistingId;
    private Book book;
    private Author author;
    private Category category;
    private BookMinProjection bookMinProjection;
    private BookDTO bookDTO;

    @BeforeEach
    public void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 2L;
        book = BookFactory.createBook();
        author = AuthorFactory.createAuthor();
        category = CategoryFactory.createCategory();
        bookMinProjection = new BookMinProjection() {

            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getTitle() {
                return "book";
            }

            @Override
            public String getName() {
                return "George Orwell";
            }

            @Override
            public Integer getPages() {
                return 100;
            }

            @Override
            public String getIsbn() {
                return "123456";
            }
            
        };

        bookDTO = new BookDTO(book);
    }

    @Test
    public void findAllShouldReturnPageOfBookMinDTO(){
        Page<Book> page = new PageImpl<>(List.of(book));
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.searchBooksWithCategories(pageable)).thenReturn(page);

        Page<BookMinDTO> response = bookService.findAll(pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
        assertThat(response.getContent().get(0).getIsbn()).isEqualTo("123456");
        assertThat(response.getContent().get(0).getAuthor()).isEqualTo("George Orwell");
        assertThat(response.getContent().get(0).getCategories().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getCategories().get(0).getName()).isEqualTo("Fiction");
    }

    @Test
    public void findByIdShouldReturnBookDTOWhenBookExists(){
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(book));

        BookDTO response = bookService.findById(existingId);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("book");
        assertThat(response.getDescription()).isEqualTo("book");
        assertThat(response.getIsbn()).isEqualTo("123456");
        assertThat(response.getCategories().get(0).getId()).isEqualTo(1L);
        assertThat(response.getCategories().get(0).getName()).isEqualTo("Fiction");
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(nonExistingId)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void findBooksByAuthorShouldReturnPageOfBookMinDTOWhenAuthorExists(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(List.of(book));

        when(authorRepository.findById(existingId)).thenReturn(Optional.of(author));
        when(bookRepository.findAllByAuthor(pageable, author)).thenReturn(page);

        Page<BookMinDTO> response = bookService.findAllByAuthor(existingId, pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
        assertThat(response.getContent().get(0).getIsbn()).isEqualTo("123456");
        assertThat(response.getContent().get(0).getAuthor()).isEqualTo("George Orwell");
        assertThat(response.getContent().get(0).getCategories().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getCategories().get(0).getName()).isEqualTo("Fiction");
    }

    @Test
    public void findBooksByAuthorShouldThrowResourceNotFoundExceptionWhenAuthorDoesNotExist(){
        Pageable pageable = PageRequest.of(0, 10);
        when(authorRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findAllByAuthor(nonExistingId,pageable)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void findBooksByCategoryShouldReturnPageOfBookMinDTOWhenCategoryExists(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookMinProjection> page = new PageImpl<>(List.of(bookMinProjection));

        when(bookRepository.findById(existingId)).thenReturn(Optional.of(book));
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category));
        when(categoryRepository.findProductsByCategory(existingId,pageable)).thenReturn(page);

        Page<BookMinDTO> response = bookService.findAllByCategory(existingId, pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("book");
        assertThat(response.getContent().get(0).getIsbn()).isEqualTo("123456");
        assertThat(response.getContent().get(0).getAuthor()).isEqualTo("George Orwell");
        assertThat(response.getContent().get(0).getCategories().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getCategories().get(0).getName()).isEqualTo("Fiction");
    }

    @Test
    public void findBooksByCategoryShouldThrowResourceNotFoundExceptionWhenCategoryDoesNotExist(){
        Pageable pageable = PageRequest.of(0, 10);
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findAllByCategory(nonExistingId,pageable)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void insertShouldReturnBookDTOWhenDataIsValid(){
        when(authorRepository.findById(existingId)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO response = bookService.insert(bookDTO);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("book");
        assertThat(response.getDescription()).isEqualTo("book");
        assertThat(response.getIsbn()).isEqualTo("123456");
        assertThat(response.getCategories().get(0).getId()).isEqualTo(1L);
        assertThat(response.getCategories().get(0).getName()).isEqualTo("Fiction");
    }

    @Test
    public void insertShouldThrowResourceNotFoundExceptionWhenAuthorDoesNotExist(){
        bookDTO.getAuthor().setId(nonExistingId);
        when(authorRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.insert(bookDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void insertShouldThrowResourceNotFoundExceptionWhenCategoryDoesNotExist(){
        bookDTO.getCategories().get(0).setId(nonExistingId);
        when(authorRepository.findById(existingId)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.insert(bookDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void updateShouldReturnBookDTOWhenDataIsValid(){
        when(bookRepository.getReferenceById(existingId)).thenReturn(book);
        when(authorRepository.findById(existingId)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO response = bookService.update(existingId,bookDTO);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("book");
        assertThat(response.getDescription()).isEqualTo("book");
        assertThat(response.getIsbn()).isEqualTo("123456");
        assertThat(response.getCategories().get(0).getId()).isEqualTo(1L);
        assertThat(response.getCategories().get(0).getName()).isEqualTo("Fiction");
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenAuthorDoesNotExist(){
        bookDTO.getAuthor().setId(nonExistingId);
        when(bookRepository.getReferenceById(existingId)).thenReturn(book);
        when(authorRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(existingId,bookDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenCategoryDoesNotExist(){
        bookDTO.getCategories().get(0).setId(nonExistingId);
        when(bookRepository.getReferenceById(existingId)).thenReturn(book);
        when(authorRepository.findById(existingId)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(existingId,bookDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenBookDoesNotExist(){
        when(bookRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> bookService.update(nonExistingId,bookDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void deleteShouldThrowNoExceptionWhenBookExists(){
        when(bookRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(existingId);

        assertThatCode(() -> bookService.deleteById(existingId)).doesNotThrowAnyException();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenBookDoesNotExist(){
        when(bookRepository.existsById(nonExistingId)).thenReturn(false);

        assertThatThrownBy(() -> bookService.deleteById(nonExistingId)).isInstanceOf(ResourceNotFoundException.class);
    }

}
