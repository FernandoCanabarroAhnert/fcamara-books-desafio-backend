package com.fernandocanabarro.desafio_fcamara_books.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "password_recovers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String token;

    private String email;

    private Instant expiration;
}
