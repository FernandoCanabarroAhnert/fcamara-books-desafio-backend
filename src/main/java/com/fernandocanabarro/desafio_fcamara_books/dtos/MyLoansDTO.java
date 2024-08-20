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
public class MyLoansDTO {

    private Long id;
    private CopyMinResponseDTO copy;
    private LocalDate loanDate;
    private LocalDate dateToReturn;
    private Boolean returned;
    private LocalDate returnedAt;
    
    public MyLoansDTO(Loan entity){
        id = entity.getId();
        copy = new CopyMinResponseDTO(entity.getCopy());
        loanDate = entity.getLoanDate();
        dateToReturn = entity.getDateToReturn();
        returned = entity.getReturned();
        returnedAt = entity.getReturnedAt();
    }
}
