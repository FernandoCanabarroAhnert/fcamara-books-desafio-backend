package com.fernandocanabarro.desafio_fcamara_books.factories;

import java.time.LocalDate;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AddressRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.UserDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Address;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;

public class UserFactory {

    private static AddressRequestDTO address = new AddressRequestDTO("91349900", "123", "1800");
    private static Address addressEntity = new Address(1L, "Avenida João Wallig", "123", "1800", "Passo da Areia", "91349900", "Porto Alegre", "RS");
    private static User user = new User(1L, "Bob Grey", "668.953.524-06", "bob@gmail.com", "123456aZ@", LocalDate.parse("2005-10-28"), addressEntity);

    public static RegistrationRequestDTO createRegistrationRequestDTO(){
        return new RegistrationRequestDTO("Bob Grey", "668.953.524-06", "bob@gmail.com", "123456aZ@", "28/10/2005", address);
    }

    public static LoginRequestDTO createLoginRequestDTO(){
        return new LoginRequestDTO("alex@gmail.com", "123456");
    }

    public static UserDTO createUserDTO(){
        return new UserDTO(user);
    }

    public static Address createAddress(){
        return addressEntity;
    }

    public static AddressRequestDTO createAddressRequestDTO(){
        return address;
    }

    public static User createUser(){
        return user;
    }
}
