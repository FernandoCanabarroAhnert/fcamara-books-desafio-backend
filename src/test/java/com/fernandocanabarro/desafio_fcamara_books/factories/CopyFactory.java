package com.fernandocanabarro.desafio_fcamara_books.factories;

import com.fernandocanabarro.desafio_fcamara_books.entities.Book;
import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;

public class CopyFactory {

    public static Copy createCopy(){
        Book book = BookFactory.createBook();
        return new Copy(1L,book,true);
    }
}
