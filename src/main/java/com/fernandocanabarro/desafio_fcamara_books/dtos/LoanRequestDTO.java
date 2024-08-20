package com.fernandocanabarro.desafio_fcamara_books.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDTO {

    @NotNull(message = "Campo Requerido")
    private Long copyId;
}
