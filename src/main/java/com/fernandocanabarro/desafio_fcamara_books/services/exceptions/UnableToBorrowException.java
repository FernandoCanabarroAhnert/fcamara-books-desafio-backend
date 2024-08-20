package com.fernandocanabarro.desafio_fcamara_books.services.exceptions;

public class UnableToBorrowException extends RuntimeException{

    public UnableToBorrowException(String msg){
        super(msg);
    }
}
