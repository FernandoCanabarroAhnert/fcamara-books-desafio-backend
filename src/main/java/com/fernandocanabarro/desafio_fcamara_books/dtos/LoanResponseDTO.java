package com.fernandocanabarro.desafio_fcamara_books.dtos;

import java.time.LocalDate;

import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {

    private Long id;
    private CopyMinResponseDTO copy;
    private UserDTO user;
    private LocalDate loanDate;
    private LocalDate dateToReturn;

    public LoanResponseDTO(Loan entity){
        id = entity.getId();
        copy = new CopyMinResponseDTO(entity.getCopy());
        user = new UserDTO(entity.getUser());
        loanDate = entity.getLoanDate();
        dateToReturn = entity.getDateToReturn();
    }
}
