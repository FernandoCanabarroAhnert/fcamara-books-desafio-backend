package com.fernandocanabarro.desafio_fcamara_books.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.utils.TokenUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class LoanControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private LoanRequestDTO dto;
    private String adminUsername,userUsername,adminPassword,userPassword;
    private String bearerTokenAdmin,bearerTokenUser;

    @BeforeEach
    public void setup() throws Exception{
        dto = new LoanRequestDTO(1L);
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        userUsername = "ana@gmail.com";
        userPassword = "123456";
        bearerTokenAdmin = TokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword, objectMapper);
        bearerTokenUser = TokenUtil.obtainAccessToken(mockMvc, userUsername, userPassword, objectMapper);
    }

    @Test
    public void makeLoanShouldReturnHttpStatus201WhenDataIsValidAndUserIsLogged() throws Exception{
        mockMvc.perform(post("/loans")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.copy.id").value(1L))
            .andExpect(jsonPath("$.copy.title").value("1984"))
            .andExpect(jsonPath("$.copy.pages").value(328))
            .andExpect(jsonPath("$.copy.isbn").value("9780451524935"))
            .andExpect(jsonPath("$.copy.author").value("George Orwell"))
            .andExpect(jsonPath("$.user.fullName").value("Ana Brown"));
    }

    //3, '9780545582957', 'Harry Potter and the Prisoner of Azkaban', 'The third book in the Harry Potter series, where Harry discovers more about his past.', 435
    @Test
    public void makeLoanShouldReturnHttpStatus201WhenDataIsValidAndAdminIsLogged() throws Exception{
        dto.setCopyId(30L);
        mockMvc.perform(post("/loans")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.copy.id").value(30L))
            .andExpect(jsonPath("$.copy.title").value("Harry Potter and the Prisoner of Azkaban"))
            .andExpect(jsonPath("$.copy.pages").value(435))
            .andExpect(jsonPath("$.copy.isbn").value("9780545582957"))
            .andExpect(jsonPath("$.copy.author").value("J.K. Rowling"))
            .andExpect(jsonPath("$.user.fullName").value("Alex Green"));
    }

    @Test
    public void makeLoanShouldReturnHttpStatus401WhenNoUserIsLogged() throws Exception{
        mockMvc.perform(post("/loans")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void makeLoanShouldReturnHttpStatus422WhenCopyIdIsNull() throws Exception{
        dto.setCopyId(null);
        mockMvc.perform(post("/loans")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity());
    }

     @Test
    public void returnLoanShouldReturnHttpStatus200WhenDataIsValidAndUserIsLogged() throws Exception{
        mockMvc.perform(post("/loans")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.copy.id").value(1L))
            .andExpect(jsonPath("$.copy.title").value("1984"))
            .andExpect(jsonPath("$.copy.pages").value(328))
            .andExpect(jsonPath("$.copy.isbn").value("9780451524935"))
            .andExpect(jsonPath("$.copy.author").value("George Orwell"))
            .andExpect(jsonPath("$.user.fullName").value("Ana Brown"));

        mockMvc.perform(put("/loans/return")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Cópia retornada com Sucesso"));
    }

    @Test
    public void returnLoanShouldReturnHttpStatus200WhenDataIsValidAndAdminIsLogged() throws Exception{
        mockMvc.perform(put("/loans/return")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Cópia retornada com Sucesso"));
    }

    @Test
    public void returnLoanShouldReturnHttpStatus401WhenNoUserIsLogged() throws Exception{
        mockMvc.perform(put("/loans/return")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void returnLoanShouldReturnHttpStatus422WhenCopyIdIsNull() throws Exception{
        dto.setCopyId(null);
        mockMvc.perform(put("/loans/return")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void findAllLoansShouldReturnHttpStatus200WhenAdminIsLogged() throws Exception{
        mockMvc.perform(get("/loans")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].dateToReturn").value("2024-08-18"))
            .andExpect(jsonPath("$.content[0].loanDate").value("2024-08-01"))
            .andExpect(jsonPath("$.content[0].returned").value(true))
            .andExpect(jsonPath("$.content[0].userId").value(1L))
            .andExpect(jsonPath("$.content[0].userEmail").value("alex@gmail.com"))
            .andExpect(jsonPath("$.content[0].copy.id").value(1L))
            .andExpect(jsonPath("$.content[0].copy.title").value("1984"));
    }

    @Test
    public void findAllLoansShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(get("/loans")
            .accept(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void findAllLoansShouldReturnHttpStatus403WhenUserIsLogged() throws Exception{
        mockMvc.perform(get("/loans")
        .header("Authorization", "Bearer " +  bearerTokenUser)
            .accept(APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void findMyLoansShouldReturnHttpStatus200WhenAdminIsLogged() throws Exception{
        mockMvc.perform(get("/loans/myLoans")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].dateToReturn").value("2024-08-18"))
            .andExpect(jsonPath("$.content[0].loanDate").value("2024-08-01"))
            .andExpect(jsonPath("$.content[0].returned").value(true))
            .andExpect(jsonPath("$.content[0].copy.id").value(1L))
            .andExpect(jsonPath("$.content[0].copy.title").value("1984"));
    }

    @Test
    public void findMyLoansShouldReturnHttpStatus200WhenUserIsLogged() throws Exception{
        mockMvc.perform(get("/loans/myLoans")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(2L))
            .andExpect(jsonPath("$.content[0].dateToReturn").value("2024-08-18"))
            .andExpect(jsonPath("$.content[0].loanDate").value("2024-08-02"))
            .andExpect(jsonPath("$.content[0].returned").value(false))
            .andExpect(jsonPath("$.content[0].copy.id").value(2L))
            .andExpect(jsonPath("$.content[0].copy.title").value("1984"));
    }

    @Test
    public void findMyLoansShouldReturnHttpStatus401WhenNoOneIsLogged() throws Exception{
        mockMvc.perform(get("/loans/myLoans")
            .accept(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }



}
