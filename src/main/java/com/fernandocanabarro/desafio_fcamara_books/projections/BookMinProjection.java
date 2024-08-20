package com.fernandocanabarro.desafio_fcamara_books.projections;

public interface BookMinProjection {

    Long getId();
    String getTitle();
    String getName();
    Integer getPages();
    String getIsbn();
}
