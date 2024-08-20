package com.fernandocanabarro.desafio_fcamara_books.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fernandocanabarro.desafio_fcamara_books.entities.Role;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;

import java.util.ArrayList;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String fullName;
    private String cpf;
    private String email;
    private LocalDate birthDate;
    private AddressResponseDTO address;
    private List<RoleDTO> roles = new ArrayList<>();

    public UserDTO(User entity){
        id = entity.getId();
        fullName = entity.getFullName();
        cpf = entity.getCpf();
        email = entity.getEmail();
        birthDate = entity.getBirthDate();
        address = new AddressResponseDTO(entity.getAddress());
        for (Role role : entity.getRoles()){
            roles.add(new RoleDTO(role));
        }
    }
}
