package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.controllers.BookController;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanReturnResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.MyLoansDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CopyRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.LoanRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.UserRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.UnableToBorrowException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserService userService;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;

    @Transactional
    public LoanResponseDTO makeLoan(LoanRequestDTO dto){
        Loan loan = new Loan();
        User user = userService.authenticated();
        Copy copy = copyRepository.findById(dto.getCopyId()).orElseThrow(() -> 
            new ResourceNotFoundException(dto.getCopyId()));
        if (!user.hasntExcededLateReturnsLimit()) {
            throw new UnableToBorrowException("O Usuário não pode realizar o Empréstimo pois excedeu a quantidade limite de atrasos");
        }
        if (user.hasTheBorrowedBook(copy)) {
            throw new UnableToBorrowException("O Usuário não pode pegar emprestado uma cópia de um livro que já possui");
        }
        toEntity(loan, dto,user,copy);
        loan = loanRepository.save(loan);
        user.addLoan(loan);
        userRepository.save(user);
        copy.addLoan(loan);
        copyRepository.save(copy);
        return new LoanResponseDTO(loan);
    }

    private void toEntity(Loan loan, LoanRequestDTO dto,User user, Copy copy){
        loan.setCopy(copy);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDateToReturn(LocalDate.now().plusDays(7L));
        loan.setReturned(false);
        loan.setReturnedAt(null);
    }

    @Transactional(readOnly = true)
    public Page<MyLoansDTO> findMyLoans(Pageable pageable){
        User user = userService.authenticated();
        Page<MyLoansDTO> page = loanRepository.findByUser(pageable, user).map(MyLoansDTO::new);
        for (MyLoansDTO x : page.getContent()){
            x.getCopy().add(linkTo(methodOn(BookController.class).findById(x.getCopy().getBookId())).withRel("Consultar Livro por Id"));
        }
        return page;
    }

    @Transactional
    public LoanReturnResponseDTO returnLoan(LoanRequestDTO dto){
        User user = userService.authenticated();
        Copy copy = copyRepository.findById(dto.getCopyId()).orElseThrow(() -> 
            new ResourceNotFoundException(dto.getCopyId()));
        if (user.hasTheBorrowedBook(copy)) {
            copy.setAvailable(true);
            Loan loan = user.findLoan(copy);
            loan.setReturned(true);
            loan.setReturnedAt(LocalDate.now());
            loanRepository.save(loan);
        }
        else 
            throw new ResourceNotFoundException("O Usuário não possui nenhum empréstimo ativo com esta cópia");
        return new LoanReturnResponseDTO("Cópia retornada com Sucesso");
    }

    public Page<LoanDTO> findAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable).map(LoanDTO::new);
    }

}
