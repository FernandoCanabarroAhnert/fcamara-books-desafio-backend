package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanReturnResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.MyLoansDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    @Transactional
    public LoanResponseDTO makeLoan(LoanRequestDTO dto){
        return null;
    }

    private void toEntity(Loan loan, LoanRequestDTO dto,User user, Copy copy){
        return;
    }

    @Transactional(readOnly = true)
    public Page<MyLoansDTO> findMyLoans(Pageable pageable){
        return null;
    }

    @Transactional
    public LoanReturnResponseDTO returnLoan(LoanRequestDTO dto){
        return null;
    }

    public Page<LoanDTO> findAllLoans(Pageable pageable) {
        return null;
    }

}
