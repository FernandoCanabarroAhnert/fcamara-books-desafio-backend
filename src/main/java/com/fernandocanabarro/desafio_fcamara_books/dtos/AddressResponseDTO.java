package com.fernandocanabarro.desafio_fcamara_books.dtos;

import com.fernandocanabarro.desafio_fcamara_books.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO{

    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public AddressResponseDTO(Address entity){
        id = entity.getId();
        cep = entity.getCep();
        logradouro = entity.getLogradouro();
        numero = entity.getNumero();
        complemento = entity.getComplemento();
        bairro = entity.getBairro();
        cidade = entity.getCidade();
        estado = entity.getEstado();
    }

    
}
