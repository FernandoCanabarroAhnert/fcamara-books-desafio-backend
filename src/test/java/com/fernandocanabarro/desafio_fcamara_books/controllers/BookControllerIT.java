package com.fernandocanabarro.desafio_fcamara_books.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookDTO;
import com.fernandocanabarro.desafio_fcamara_books.factories.BookFactory;
import com.fernandocanabarro.desafio_fcamara_books.utils.TokenUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId,nonExistingId;
    private BookDTO bookDTO;
    private String adminUsername,userUsername,adminPassword,userPassword;
    private String bearerTokenAdmin,bearerTokenUser;

    @BeforeEach
    public void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 999L;
        bookDTO = new BookDTO(BookFactory.createBook());
        bookDTO.setId(null);
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        userUsername = "ana@gmail.com";
        userPassword = "123456";
        bearerTokenAdmin = TokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword, objectMapper);
        bearerTokenUser = TokenUtil.obtainAccessToken(mockMvc, userUsername, userPassword, objectMapper);
    }

    @Test
    public void findAllShouldReturnHttpStatus200AndPageOfBookMinDTO() throws Exception{
        mockMvc.perform(get("/books")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].title").value("1984"))
            .andExpect(jsonPath("$.content[0].author").value("George Orwell"))
            .andExpect(jsonPath("$.content[0].pages").value(328))
            .andExpect(jsonPath("$.content[0].isbn").value("9780451524935"))
            .andExpect(jsonPath("$.content[0].categories[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].categories[0].name").value("Fiction"));
    }

    @Test
    public void findByIdShouldReturnHttpStatus200WhenIdExists() throws Exception{
        mockMvc.perform(get("/books/{id}",existingId)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("1984"))
            .andExpect(jsonPath("$.description").value("A dystopian novel set in a totalitarian society under constant surveillance."))
            .andExpect(jsonPath("$.pages").value(328))
            .andExpect(jsonPath("$.isbn").value("9780451524935"))
            .andExpect(jsonPath("$.author.id").value(1L))
            .andExpect(jsonPath("$.author.name").value("George Orwell"))
            .andExpect(jsonPath("$.categories[0].id").value(1L))
            .andExpect(jsonPath("$.categories[0].name").value("Fiction"));
    }

    @Test
    public void findByIdShouldReturnHttpStatus404WhenIdDoesNotExist() throws Exception{
        mockMvc.perform(get("/books/{id}",nonExistingId)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        mockMvc.perform(post("/books")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("book"))
            .andExpect(jsonPath("$.description").value("book"))
            .andExpect(jsonPath("$.isbn").value("123456"))
            .andExpect(jsonPath("$.author.id").value(1L))
            .andExpect(jsonPath("$.author.name").value("George Orwell"))
            .andExpect(jsonPath("$.categories[0].id").value(1L))
            .andExpect(jsonPath("$.categories[0].name").value("Fiction"));
    }

    @Test
    public void insertShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(post("/books")
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void insertShouldReturnHttpStatus403WhenUserIsLogged() throws Exception{
        mockMvc.perform(post("/books")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void insertShoulReturnHttpStatus422WhenTitleIsBlank() throws Exception{
        bookDTO.setTitle("");
        mockMvc.perform(post("/books")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("title"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void insertShoulReturnHttpStatus422WhenDescriptionIsBlank() throws Exception{
        bookDTO.setDescription("");
        mockMvc.perform(post("/books")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("description"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void insertShoulReturnHttpStatus422WhenIsbnIsBlank() throws Exception{
        bookDTO.setIsbn("");
        mockMvc.perform(post("/books")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("isbn"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void insertShoulReturnHttpStatus422BookDTOHasNoCategories() throws Exception{
        bookDTO.getCategories().clear();
        mockMvc.perform(post("/books")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("categories"))
            .andExpect(jsonPath("$.errors[0].message").value("Livro deve ter ao menos uma Categoria"));
    }

    @Test
    public void updateShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        mockMvc.perform(put("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("book"))
            .andExpect(jsonPath("$.description").value("book"))
            .andExpect(jsonPath("$.isbn").value("123456"))
            .andExpect(jsonPath("$.author.id").value(1L))
            .andExpect(jsonPath("$.author.name").value("George Orwell"))
            .andExpect(jsonPath("$.categories[0].id").value(1L))
            .andExpect(jsonPath("$.categories[0].name").value("Fiction"));
    }

    @Test
    public void updateShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(put("/books/{id}",existingId)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateShouldReturnHttpStatus403WhenUserIsLogged() throws Exception{
        mockMvc.perform(put("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenUser)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void updateShouldReturnHttpStatus404WhenProductDoesNotExist() throws Exception{
        mockMvc.perform(put("/books/{id}",nonExistingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateShoulReturnHttpStatus422WhenTitleIsBlank() throws Exception{
        bookDTO.setTitle("");
        mockMvc.perform(put("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("title"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void updateShoulReturnHttpStatus422WhenDescriptionIsBlank() throws Exception{
        bookDTO.setDescription("");
        mockMvc.perform(put("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("description"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void updateShoulReturnHttpStatus422WhenIsbnIsBlank() throws Exception{
        bookDTO.setIsbn("");
        mockMvc.perform(put("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("isbn"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void updateShoulReturnHttpStatus422BookDTOHasNoCategories() throws Exception{
        bookDTO.getCategories().clear();
        mockMvc.perform(put("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(bookDTO))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("categories"))
            .andExpect(jsonPath("$.errors[0].message").value("Livro deve ter ao menos uma Categoria"));
    }

    @Test
    public void deleteShouldReturnHttpStatus204WhenIdExistsAndAdminIsLogged() throws Exception{
        mockMvc.perform(delete("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(delete("/books/{id}",existingId)
            .accept(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteShouldReturnHttpStatus403WhenUserExists() throws Exception{
        mockMvc.perform(delete("/books/{id}",existingId)
            .header("Authorization", "Bearer " + bearerTokenUser)
            .accept(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }


}
