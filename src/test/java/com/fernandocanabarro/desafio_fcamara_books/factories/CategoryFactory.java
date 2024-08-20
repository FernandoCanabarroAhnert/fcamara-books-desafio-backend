package com.fernandocanabarro.desafio_fcamara_books.factories;

import com.fernandocanabarro.desafio_fcamara_books.entities.Category;

public class CategoryFactory {

    public static Category createCategory(){
        return new Category(1L,"Fiction");
    }
}
