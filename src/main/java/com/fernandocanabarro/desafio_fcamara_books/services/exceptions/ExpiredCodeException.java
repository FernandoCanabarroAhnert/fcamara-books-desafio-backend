package com.fernandocanabarro.desafio_fcamara_books.services.exceptions;

public class ExpiredCodeException extends RuntimeException{

    public ExpiredCodeException(String msg){
        super(msg);
    }
}
