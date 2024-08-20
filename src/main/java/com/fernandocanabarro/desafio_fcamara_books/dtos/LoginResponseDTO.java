package com.fernandocanabarro.desafio_fcamara_books.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO extends RepresentationModel<LoginResponseDTO>{

    public String accessToken;
    public Long expiresIn;
}
