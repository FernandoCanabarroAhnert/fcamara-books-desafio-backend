package com.fernandocanabarro.desafio_fcamara_books.factories;

import com.fernandocanabarro.desafio_fcamara_books.entities.Author;

public class AuthorFactory {

    public static Author createAuthor(){
        return new Author(1L, "George Orwell");
    }
}
