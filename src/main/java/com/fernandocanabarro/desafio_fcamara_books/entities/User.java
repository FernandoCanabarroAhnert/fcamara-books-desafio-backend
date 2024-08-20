package com.fernandocanabarro.desafio_fcamara_books.entities;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails,Principal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    private String cpf;

    private String email;

    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "late_return")
    private Integer lateReturn;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();

    private Boolean activated;

    public User(Long id, String fullName, String cpf, String email, String password, LocalDate birthDate,
            Address address) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.address = address;
    }

    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public boolean hasRole(String rolename){
        for (Role role : roles){
            if (role.getAuthority().equals(rolename)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAbleToBorrow(Copy copy){
        return hasntExcededLateReturnsLimit() && !hasTheBorrowedBook(copy);
    }

    public boolean hasntExcededLateReturnsLimit(){
        return this.lateReturn <= 2;
    }

    public boolean hasTheBorrowedBook(Copy copy){
        Optional<Copy> borrowedCopy = loans
                                        .stream()
                                        .filter(x -> x.getCopy().getId() == copy.getId())
                                        .filter(x -> !x.getReturned()).findFirst()
                                        .map(x -> x.getCopy());
        return borrowedCopy.isPresent();
    }

    public void addLoan(Loan loan){
        this.loans.add(loan);
    }

    public Loan findLoan(Copy copy) {
        return this.getLoans().stream().filter(x -> x.getCopy().getId().equals(copy.getId())).filter(x -> !x.getReturned()).findFirst().get();
    }
}
