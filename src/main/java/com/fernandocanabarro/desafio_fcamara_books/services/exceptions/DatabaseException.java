package com.fernandocanabarro.desafio_fcamara_books.services.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String msg){
        super(msg);
    }
}
