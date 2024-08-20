package com.fernandocanabarro.desafio_fcamara_books.services.exceptions;

public class AccountNotActivatedException extends RuntimeException{

    public AccountNotActivatedException(String msg){
        super(msg);
    }
}
