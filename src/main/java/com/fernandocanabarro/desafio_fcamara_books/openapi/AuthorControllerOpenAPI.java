package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AuthorDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface AuthorControllerOpenAPI {

    @Operation(
    description = "Consultar Autores",
    summary = "Endpoint responsável por receber a requisição da Consulta de Autores",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    ResponseEntity<Page<AuthorDTO>> findAll(Pageable pageable);

    @Operation(
    description = "Consultar Livros de um dado Autor",
    summary = "Endpoint responsável por receber a requisição da Consulta de Livros por Autor",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "Autor Não Encontrado", responseCode = "404")
   		}
	)
    ResponseEntity<Page<BookMinDTO>> findAllBooksByAuthor(@PathVariable Long id, Pageable pageable);

    @Operation(
    description = "Inserir Autor",
    summary = "Endpoint responsável por receber a requisição de Inserir Autor",
    responses = {
         @ApiResponse(description = "Autor Criado", responseCode = "201"),
         @ApiResponse(description = "Autor já Existe", responseCode = "400"),
         @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
         @ApiResponse(description = "Quando um Usuário Comum faz a requisição", responseCode = "403"),
         @ApiResponse(description = "Algum Campo do Autor está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<AuthorDTO> insert(@RequestBody @Valid AuthorDTO dto);

}
