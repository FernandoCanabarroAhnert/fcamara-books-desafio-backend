package com.fernandocanabarro.desafio_fcamara_books.factories;

import com.fernandocanabarro.desafio_fcamara_books.entities.Author;
import com.fernandocanabarro.desafio_fcamara_books.entities.Book;

public class BookFactory {

    public static Book createBook(){
        Author author = AuthorFactory.createAuthor();
        Book book = new Book(1L, "book", "book",author , 100, "123456");
        book.getCategories().add(CategoryFactory.createCategory());
        return book;
    }
}
