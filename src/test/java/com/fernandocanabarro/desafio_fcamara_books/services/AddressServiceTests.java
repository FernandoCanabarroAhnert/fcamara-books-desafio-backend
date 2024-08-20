package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AddressRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.ViaCepResponse;
import com.fernandocanabarro.desafio_fcamara_books.entities.Address;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private RestTemplate restTemplate = new RestTemplate();

    private AddressRequestDTO addressRequestDTO;
    private ViaCepResponse viaCepResponse;

    @BeforeEach
    public void setup() throws Exception{
        addressRequestDTO = new AddressRequestDTO("91349900", "123", "1800");
        viaCepResponse = new ViaCepResponse("91349900", "logradouro", "1800", "bairro", "localidade", "uf");
    }

    @Test
    public void getAddressFromCepShouldReturnAddressWhenCEPExists(){
        ResponseEntity<ViaCepResponse> responseEntity = new ResponseEntity<>(viaCepResponse,HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(ViaCepResponse.class))).thenReturn(responseEntity);

        Address response = addressService.getAddressFromCep(addressRequestDTO);

        assertEquals("logradouro", response.getLogradouro());
        assertEquals("1800", response.getComplemento());
        assertEquals("bairro", response.getBairro());
        assertEquals("91349900", response.getCep());
        assertEquals("localidade", response.getCidade());
        assertEquals("uf", response.getEstado());
        assertEquals("123", response.getNumero());
    }

    @Test
    public void getAddressShouldThrowResourceNotFoundExceptionWhenCEPIsInvalid(){
        addressRequestDTO.setCep("00000000");

        when(restTemplate.getForEntity(anyString(), eq(ViaCepResponse.class))).thenThrow(BadRequest.class);

        assertThatThrownBy(() -> addressService.getAddressFromCep(addressRequestDTO)).isInstanceOf(ResourceNotFoundException.class);
    }
}
