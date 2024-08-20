package com.fernandocanabarro.desafio_fcamara_books.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanReturnResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.MyLoansDTO;
import com.fernandocanabarro.desafio_fcamara_books.services.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<LoanResponseDTO> makeLoan(@RequestBody @Valid LoanRequestDTO dto){
        LoanResponseDTO obj = loanService.makeLoan(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/myLoans")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Page<MyLoansDTO>> findMyLoans(Pageable pageable){
        Page<MyLoansDTO> page = loanService.findMyLoans(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/return")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<LoanReturnResponseDTO> returnLoan(@RequestBody @Valid LoanRequestDTO dto){
        LoanReturnResponseDTO obj = loanService.returnLoan(dto);
        return ResponseEntity.ok(obj);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<LoanDTO>> findAllLoans(Pageable pageable){
        Page<LoanDTO> page = loanService.findAllLoans(pageable);
        return ResponseEntity.ok(page);
    }
}
