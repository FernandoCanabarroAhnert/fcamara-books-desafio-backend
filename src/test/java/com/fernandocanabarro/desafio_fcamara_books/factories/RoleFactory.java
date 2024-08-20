package com.fernandocanabarro.desafio_fcamara_books.factories;

import com.fernandocanabarro.desafio_fcamara_books.entities.Role;

public class RoleFactory {

    public static Role createRoleAdmin(){
        return new Role(1L,"ROLE_ADMIN");
    }

    public static Role createRoleUser(){
        return new Role(2L, "ROLE_USER");
    }
}
