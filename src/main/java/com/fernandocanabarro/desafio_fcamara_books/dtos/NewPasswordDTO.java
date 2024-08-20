package com.fernandocanabarro.desafio_fcamara_books.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDTO {

    @NotBlank(message = "Campo Requerido")
    private String token;
    @Size(min = 8,message = "Senha deve possuir pelo menos 8 caracteres")
    private String password;
}
