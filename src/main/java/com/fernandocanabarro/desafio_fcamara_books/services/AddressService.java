package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AddressRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.ViaCepResponse;
import com.fernandocanabarro.desafio_fcamara_books.entities.Address;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;

@Service
public class AddressService {

    private RestTemplate restTemplate = new RestTemplate();

    private String baseUrl = "https://viacep.com.br/ws/";

    public Address getAddressFromCep(AddressRequestDTO dto) {
        try{
            String url = baseUrl + dto.getCep() + "/json/";
            ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(url, ViaCepResponse.class);
            ViaCepResponse viaCepResponse = response.getBody();
            Address address = convertToAddress(viaCepResponse);
            address.setNumero(dto.getNumero());
            address.setComplemento(dto.getComplemento());
            return address; 
        }
        catch (BadRequest e){
            throw new ResourceNotFoundException("CEP não encontrado");
        }
    }

    private Address convertToAddress(ViaCepResponse viaCepResponse) {
        Address address = new Address();
        address.setLogradouro(viaCepResponse.getLogradouro());
        address.setComplemento(viaCepResponse.getComplemento());
        address.setBairro(viaCepResponse.getBairro());
        address.setCep(viaCepResponse.getCep());
        address.setCidade(viaCepResponse.getLocalidade());
        address.setEstado(viaCepResponse.getUf());
        return address;
    }
}
