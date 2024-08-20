package com.fernandocanabarro.desafio_fcamara_books.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.ActivationCode;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode,Long>{

    Optional<ActivationCode> findByCode(String code);
}
