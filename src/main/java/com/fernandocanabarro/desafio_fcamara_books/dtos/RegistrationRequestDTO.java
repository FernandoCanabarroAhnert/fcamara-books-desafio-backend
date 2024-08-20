package com.fernandocanabarro.desafio_fcamara_books.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

    @NotBlank(message = "Campo Requerido")
    private String fullName;
    @CPF(message = "CPF deve estar em um formato válido")
    private String cpf;
    @Pattern(regexp = "^[A-Za-z0-9+._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",message = "Formato do Email deve ser Válido")
    private String email;
    @Size(min = 8,message = "Senha deve possuir pelo menos 8 caracteres")
    private String password;
    @NotBlank(message = "Campo Requerido")
    private String birthDate;
    private AddressRequestDTO address;
}
