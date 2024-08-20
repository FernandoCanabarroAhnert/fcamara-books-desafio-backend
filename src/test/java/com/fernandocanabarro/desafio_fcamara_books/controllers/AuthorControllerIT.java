package com.fernandocanabarro.desafio_fcamara_books.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fernandocanabarro.desafio_fcamara_books.dtos.AuthorDTO;
import com.fernandocanabarro.desafio_fcamara_books.utils.TokenUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AuthorControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private AuthorDTO dto;
    private String adminUsername,userUsername,adminPassword,userPassword;
    private String bearerTokenAdmin,bearerTokenUser;

    @BeforeEach
    public void setup() throws Exception{
        dto = new AuthorDTO(null,"author");
         adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        userUsername = "ana@gmail.com";
        userPassword = "123456";
        bearerTokenAdmin = TokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword, objectMapper);
        bearerTokenUser = TokenUtil.obtainAccessToken(mockMvc, userUsername, userPassword, objectMapper);
    }

    @Test
    public void findAllAuthorsShouldReturnHttpStatus200() throws Exception{
        mockMvc.perform(get("/authors")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].name").value("George Orwell"));
    }

    @Test
    public void findAllBooksByAuthorShouldReturnHttpStatus200WhenCategoryExists() throws Exception{
        mockMvc.perform(get("/authors/1/books")
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
    public void findAllBooksByAuthorShouldReturnHttpStatus404WhenCategoryDoesNotExist() throws Exception{
        mockMvc.perform(get("/authors/999/books")
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        mockMvc.perform(post("/authors")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(dto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("author"));
    }

    @Test
    public void insertShouldReturnHttpStatus400WhenAuthorAlreadyExists() throws Exception{
        dto.setName("George Orwell");
        mockMvc.perform(post("/authors")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(dto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void insertShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(post("/authors")
            .content(objectMapper.writeValueAsString(dto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void insertShouldReturnHttpStatus403WhenUserIsLogged() throws Exception{
        mockMvc.perform(post("/authors")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .content(objectMapper.writeValueAsString(dto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void insertShoulReturnHttpStatus422WhenNameIsBlank() throws Exception{
        dto.setName("");
        mockMvc.perform(post("/authors")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .content(objectMapper.writeValueAsString(dto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }


}
