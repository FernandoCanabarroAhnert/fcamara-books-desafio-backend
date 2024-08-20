package com.fernandocanabarro.desafio_fcamara_books.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAllCategoriesShoulReturnHttpStatus200() throws Exception{
        mockMvc.perform(get("/categories")
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].name").value("Fiction"));
    }

    @Test
    public void findAllBooksByCategoryShouldReturnHttpStatus200WhenCategoryExists() throws Exception{
        mockMvc.perform(get("/categories/1/books")
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
    public void findAllBooksByCategoryShouldReturnHttpStatus404WhenCategoryDoesNotExist() throws Exception{
        mockMvc.perform(get("/categories/999/books")
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
