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
public class LoanDTO {

    private Long id;
    private CopyMinResponseDTO copy;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userCpf;
    private LocalDate loanDate;
    private LocalDate dateToReturn;
    private Boolean returned;
    private LocalDate returnedAt;

    public LoanDTO(Loan entity){
        id = entity.getId();
        copy = new CopyMinResponseDTO(entity.getCopy());
        userId = entity.getUser().getId();
        userEmail = entity.getUser().getEmail();
        userName = entity.getUser().getFullName();
        userCpf = entity.getUser().getCpf();
        loanDate = entity.getLoanDate();
        dateToReturn = entity.getDateToReturn();
        returned = entity.getReturned();
        returnedAt = entity.getReturnedAt();
    }
}
