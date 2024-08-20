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
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.utils.TokenUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CopyControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private CopyRequestDTO dto;
    private String adminUsername,userUsername,adminPassword,userPassword;
    private String bearerTokenAdmin,bearerTokenUser;

    
    @BeforeEach
    public void setup() throws Exception{
        dto = new CopyRequestDTO(1L);
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        userUsername = "ana@gmail.com";
        userPassword = "123456";
        bearerTokenAdmin = TokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword, objectMapper);
        bearerTokenUser = TokenUtil.obtainAccessToken(mockMvc, userUsername, userPassword, objectMapper);
    }

    @Test
    public void findAllShouldReturnHttpStatus200AndPageOfBookMinDTO() throws Exception{
        mockMvc.perform(get("/copies")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].bookId").value(1L))
            .andExpect(jsonPath("$.content[0].title").value("1984"))
            .andExpect(jsonPath("$.content[0].author").value("George Orwell"))
            .andExpect(jsonPath("$.content[0].pages").value(328))
            .andExpect(jsonPath("$.content[0].isbn").value("9780451524935"));
    }

    @Test
    public void insertShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        mockMvc.perform(post("/copies")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.bookId").value(1L))
            .andExpect(jsonPath("$.title").value("1984"))
            .andExpect(jsonPath("$.author").value("George Orwell"))
            .andExpect(jsonPath("$.pages").value(328))
            .andExpect(jsonPath("$.isbn").value("9780451524935"));
    }

    @Test
    public void insertShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(post("/copies")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void insertShouldReturnHttpStatus403WhenUserIsLogged() throws Exception{
        mockMvc.perform(post("/copies")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldReturnHttpStatus404WhenBookIdDoesNotExist() throws Exception{
        dto.setBookId(999L);
        mockMvc.perform(post("/copies")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnHttpStatus404WhenBookIdIsNull() throws Exception{
        dto.setBookId(null);
        mockMvc.perform(post("/copies")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity());
    }
}
