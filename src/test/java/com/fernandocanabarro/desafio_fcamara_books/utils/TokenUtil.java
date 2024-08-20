package com.fernandocanabarro.desafio_fcamara_books.utils;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.json.JacksonJsonParser;

import static org.springframework.http.MediaType.APPLICATION_JSON;;

@Component
public class TokenUtil {

    public static String obtainAccessToken(MockMvc mockMvc, String username, String password, ObjectMapper objectMapper) throws Exception{
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        ResultActions result = 
            mockMvc.perform(post("/users/login")
                .content(objectMapper.writeValueAsString(loginRequestDTO))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("accessToken").toString();
    }
}
