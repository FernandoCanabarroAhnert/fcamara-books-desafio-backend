package com.fernandocanabarro.desafio_fcamara_books.projections;

public interface CopyProjection {

    Long getId();
    Long getBookId();
    String getTitle();
    String getAuthor();
    Integer getPages();
    String getIsbn();
    Boolean getAvailable();
}
