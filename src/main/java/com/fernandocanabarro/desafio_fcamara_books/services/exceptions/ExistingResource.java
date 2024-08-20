package com.fernandocanabarro.desafio_fcamara_books.services.exceptions;

public class ExistingResource extends RuntimeException{

    public ExistingResource(String msg){
        super(msg);
    }
}
