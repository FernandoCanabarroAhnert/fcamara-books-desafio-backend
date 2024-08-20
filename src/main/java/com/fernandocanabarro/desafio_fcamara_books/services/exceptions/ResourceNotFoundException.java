package com.fernandocanabarro.desafio_fcamara_books.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Long id){
        super("Resource not found! Id = " + id);
    }

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
