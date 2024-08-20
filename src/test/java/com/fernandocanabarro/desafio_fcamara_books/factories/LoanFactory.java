package com.fernandocanabarro.desafio_fcamara_books.factories;

import java.time.LocalDate;

import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;

public class LoanFactory {

    public static Loan createLoan(){
        return new Loan(1L, CopyFactory.createCopy(), UserFactory.createUser(), LocalDate.parse("2024-08-18"), LocalDate.parse("2024-08-25"), false, null);
    }
}
