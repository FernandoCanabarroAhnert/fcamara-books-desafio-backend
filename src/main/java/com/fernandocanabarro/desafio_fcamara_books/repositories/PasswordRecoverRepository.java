package com.fernandocanabarro.desafio_fcamara_books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.PasswordRecover;

import java.util.Optional;
import java.time.Instant;

@Repository
public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover,Long>{

    @Query("SELECT obj FROM PasswordRecover obj WHERE obj.token = :token AND obj.expiration > :now")
    Optional<PasswordRecover> searchValidToken(String token,Instant now);
}
