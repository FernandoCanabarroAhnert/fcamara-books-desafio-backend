package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AccountActivationResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.AddressRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.EmailDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.UserDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Address;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;

import java.time.Instant;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public UserDTO registrate(RegistrationRequestDTO dto) {
        return null;
    }

    private void toEntity(User user, RegistrationRequestDTO dto) {
        return;
    }

    @Transactional
    private Address findAndSaveAddress(AddressRequestDTO addressRequestDTO) {
        return null;
    }

    private void sendConfirmationEmail(User user) {
        return;
    }

    private String generateAndSaveActivationCode(User user) {
        return null;
    }

    private String generateCode() {
        return null;
    }

    @Transactional
    public AccountActivationResponseDTO activateAccount(String code) {
        return null;
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {
        return null;
    }

    private Instant expirationTime() {
        return null;
    }

    @Transactional
    public User authenticated() {
        return null;
    }

    @Transactional
    public void createRecoverToken(EmailDTO obj) {
        return;
    }

    @Transactional
    public void saveNewPassword(NewPasswordDTO obj) {
        return;
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        return null;
    }

}
