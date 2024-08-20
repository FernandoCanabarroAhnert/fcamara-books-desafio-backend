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
import com.fernandocanabarro.desafio_fcamara_books.dtos.EmailDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.factories.UserFactory;
import com.fernandocanabarro.desafio_fcamara_books.utils.TokenUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private RegistrationRequestDTO registrationRequestDTO;
    private String adminUsername,userUsername,adminPassword,userPassword;
    private String bearerTokenAdmin,bearerTokenUser;
    private EmailDTO emailDTO;
    private NewPasswordDTO newPasswordDTO;

    @BeforeEach
    public void setup() throws Exception{
        registrationRequestDTO = UserFactory.createRegistrationRequestDTO();
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";
        userUsername = "ana@gmail.com";
        userPassword = "123456";
        bearerTokenAdmin = TokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword, objectMapper);
        bearerTokenUser = TokenUtil.obtainAccessToken(mockMvc, userUsername, userPassword, objectMapper);
        emailDTO = new EmailDTO("alex@gmail.com");
        newPasswordDTO = new NewPasswordDTO("token", "12345Az@123");
    }
    
    @Test
    public void registerShouldReturnHttpStatus201WhenDataIsValid() throws Exception{
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.fullName").value("Bob Grey"))
            .andExpect(jsonPath("$.email").value("bob@gmail.com"))
            .andExpect(jsonPath("$.cpf").value("668.953.524-06"))
            .andExpect(jsonPath("$.roles[0].authority").value("ROLE_USER"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenFullNameIsBlank() throws Exception{
        registrationRequestDTO.setFullName("");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("fullName"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenCpfIsInvalid() throws Exception{
        registrationRequestDTO.setCpf("");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("cpf"))
            .andExpect(jsonPath("$.errors[0].message").value("CPF deve estar em um formato válido"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenEmailIsInvalid() throws Exception{
        registrationRequestDTO.setEmail("");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("email"))
            .andExpect(jsonPath("$.errors[0].message").value("Formato do Email deve ser Válido"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenBirthDateIsBlank() throws Exception{
        registrationRequestDTO.setBirthDate("");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("birthDate"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordHasLessThan8Characters() throws Exception{
        registrationRequestDTO.setPassword("1234aZ@");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve possuir pelo menos 8 caracteres"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHaveAnUpperCaseLetter() throws Exception{
        registrationRequestDTO.setPassword("12345az@");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos uma Letra Maiúscula"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHaveALowerCaseLetter() throws Exception{
        registrationRequestDTO.setPassword("12345AZ@");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos uma Letra Minúscula"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHaveAnyNumber() throws Exception{
        registrationRequestDTO.setPassword("Abcdefg@");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos um Número"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenPasswordDoesNotHaveASpecialCharacter() throws Exception{
        registrationRequestDTO.setPassword("12345Azz");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos um Caractere Especial"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenEmailAlreadyExists() throws Exception{
        registrationRequestDTO.setEmail("alex@gmail.com");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("email"))
            .andExpect(jsonPath("$.errors[0].message").value("Este Email já Existe"));
    }

    @Test
    public void registerShouldReturnHttpStatus422WhenCpfAlreadyExists() throws Exception{
        registrationRequestDTO.setCpf("066.433.542-00");
        mockMvc.perform(post("/users/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registrationRequestDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("cpf"))
            .andExpect(jsonPath("$.errors[0].message").value("Este CPF já existe"));
    }

    @Test
    public void getMeShouldReturnHttpStatus200WhenAdminIsLogged() throws Exception{
        mockMvc.perform(get("/users/me")
            .header("Authorization", "Bearer " + bearerTokenAdmin)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.fullName").value("Alex Green"))
            .andExpect(jsonPath("$.email").value("alex@gmail.com"))
            .andExpect(jsonPath("$.cpf").value("066.433.542-00"));
    }

    @Test
    public void getMeShouldReturnHttpStatus200WhenUserIsLogged() throws Exception{
        mockMvc.perform(get("/users/me")
            .header("Authorization", "Bearer " + bearerTokenUser)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2L))
            .andExpect(jsonPath("$.fullName").value("Ana Brown"))
            .andExpect(jsonPath("$.email").value("ana@gmail.com"))
            .andExpect(jsonPath("$.cpf").value("98765432100"));
    }

    @Test
    public void getMeShouldReturnHttpStatus401WhenNoUserIsLogged() throws Exception{
        mockMvc.perform(get("/users/me")
            .accept(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void activateAccountShouldReturnHttpStatus404WhenActivationCodeIsInvalid() throws Exception{
        mockMvc.perform(post("/users/activate?code=123")
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void createRecoverTokenShouldReturnHttpStatus404WhenEmailDoesNotExist() throws Exception{
        emailDTO.setEmail("fernando@gmail.com");
        mockMvc.perform(post("/users/recover-token")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(emailDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void createRecoverTokenShouldReturnHttpStatus422WhenEmailIsInvalid() throws Exception{
        emailDTO.setEmail("fernandogmail.com");
        mockMvc.perform(post("/users/recover-token")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(emailDTO)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createRecoverTokenShouldReturnHttpStatus200WhenEmailExists() throws Exception{
        mockMvc.perform(post("/users/recover-token")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(emailDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus404WhenTokenIsNotFound() throws Exception{
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus422WhenTokenIsBlank() throws Exception{
        newPasswordDTO.setToken("");
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("token"))
            .andExpect(jsonPath("$.errors[0].message").value("Campo Requerido"));
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus422WhenPasswordHasLessThan8Characters() throws Exception{
        newPasswordDTO.setPassword("1234aZ@");
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("Senha deve possuir pelo menos 8 caracteres"));
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus422WhenPasswordDoesNotHaveAnUpperCaseLetter() throws Exception{
        newPasswordDTO.setPassword("12345az@");
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos uma Letra Maiúscula"));
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus422WhenPasswordDoesNotHaveALowerCaseLetter() throws Exception{
        newPasswordDTO.setPassword("12345AZ@");
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos uma Letra Minúscula"));
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus422WhenPasswordDoesNotHaveAnyNumber() throws Exception{
        newPasswordDTO.setPassword("Abcdefg@");
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos um Número"));
    }

    @Test
    public void saveNewPasswordShouldReturnHttpStatus422WhenPasswordDoesNotHaveASpecialCharacter() throws Exception{
        newPasswordDTO.setPassword("12345Azz");
        mockMvc.perform(put("/users/new-password")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPasswordDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.errors[0].fieldName").value("password"))
            .andExpect(jsonPath("$.errors[0].message").value("A Senha deve possuir pelo menos um Caractere Especial"));
    }

}
