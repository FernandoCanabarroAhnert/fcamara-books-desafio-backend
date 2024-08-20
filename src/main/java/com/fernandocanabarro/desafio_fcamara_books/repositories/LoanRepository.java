package com.fernandocanabarro.desafio_fcamara_books.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long>{

    Page<Loan> findByUser(Pageable pageable,User user);
}
