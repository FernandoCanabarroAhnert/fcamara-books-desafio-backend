package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyMinResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface CopyControllerOpenAPI {

    @Operation(
    description = "Consultar Cópias Disponíveis",
    summary = "Endpoint responsável por receber a requisição da Consulta de Cópias Disponíveis",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    ResponseEntity<Page<CopyMinResponseDTO>> findAllAvailableCopies(Pageable pageable);

    @Operation(
    description = "Inserir Cópia",
    summary = "Endpoint responsável por receber a requisição de Inserir Cópia",
    responses = {
         @ApiResponse(description = "Cópia Criado", responseCode = "201"),
         @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
         @ApiResponse(description = "Quando um Usuário Comum faz a requisição", responseCode = "403"),
         @ApiResponse(description = "Algum Campo da Cópia está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<CopyMinResponseDTO> insert(@RequestBody @Valid CopyRequestDTO dto);
}
