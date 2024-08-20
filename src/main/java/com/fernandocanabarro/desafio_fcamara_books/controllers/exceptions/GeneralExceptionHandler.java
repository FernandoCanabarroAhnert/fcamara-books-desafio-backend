package com.fernandocanabarro.desafio_fcamara_books.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fernandocanabarro.desafio_fcamara_books.dtos.exceptions.StandardError;
import com.fernandocanabarro.desafio_fcamara_books.dtos.exceptions.ValidationError;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.AccountNotActivatedException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.DatabaseException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.EmailException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ExistingResource;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ExpiredCodeException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.UnableToBorrowException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> notFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> invalidData(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(),status.value(),"Dados Inválidos",request.getRequestURI());
        for (FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ExistingResource.class)
    public ResponseEntity<StandardError> existingResource(ExistingResource e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AccountNotActivatedException.class)
    public ResponseEntity<StandardError> accountNotActivated(AccountNotActivatedException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<StandardError> email(EmailException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ExpiredCodeException.class)
    public ResponseEntity<StandardError> expiredCode(ExpiredCodeException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnableToBorrowException.class)
    public ResponseEntity<StandardError> unableToBorrow(UnableToBorrowException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
