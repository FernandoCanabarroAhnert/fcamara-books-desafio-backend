package com.fernandocanabarro.desafio_fcamara_books.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AccountActivationResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.EmailDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.UserDTO;
import com.fernandocanabarro.desafio_fcamara_books.openapi.UserControllerOpenAPI;
import com.fernandocanabarro.desafio_fcamara_books.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerOpenAPI{

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationRequestDTO dto){
        UserDTO obj = userService.registrate(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @PostMapping("/activate")
    public ResponseEntity<AccountActivationResponseDTO> activate(@RequestParam(name = "code",defaultValue = "") String code){
        return ResponseEntity.ok(userService.activateAccount(code));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto){
        LoginResponseDTO obj = userService.login(dto);
        return ResponseEntity.ok(obj);
    }

    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@RequestBody @Valid EmailDTO obj){
        userService.createRecoverToken(obj);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/new-password")
    public ResponseEntity<Void> saveNewPassword(@RequestBody @Valid NewPasswordDTO obj){
        userService.saveNewPassword(obj);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<UserDTO> getMe(){
        return ResponseEntity.ok(userService.getMe());
    }
}
