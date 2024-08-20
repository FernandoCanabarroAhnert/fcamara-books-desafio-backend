package com.fernandocanabarro.desafio_fcamara_books.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "copies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Boolean available;

    @OneToMany(mappedBy = "copy")
    private List<Loan> loans = new ArrayList<>();

    public Copy(Long id, Book book, Boolean available) {
        this.id = id;
        this.book = book;
        this.available = available;
    }

    public void addLoan(Loan loan){
        this.loans.add(loan);
    }

    
}
