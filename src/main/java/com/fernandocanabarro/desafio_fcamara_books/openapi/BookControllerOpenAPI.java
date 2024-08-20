package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fernandocanabarro.desafio_fcamara_books.dtos.BookDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface BookControllerOpenAPI {

    @Operation(
    description = "Consultar Livros",
    summary = "Endpoint responsável por receber a requisição da Consulta de Livros",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    ResponseEntity<Page<BookMinDTO>> findAll(Pageable pageable);

    @Operation(
    description = "Consultar Livro por Id",
    summary = "Endpoint responsável por receber a requisição da Consulta de Livro por Id",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "Livro Não Encontrado", responseCode = "404")
   		}
	)
    ResponseEntity<BookDTO> findById(@PathVariable Long id);

    @Operation(
    description = "Inserir Livro",
    summary = "Endpoint responsável por receber a requisição de Inserir Livro",
    responses = {
         @ApiResponse(description = "Livro Criado", responseCode = "201"),
         @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
         @ApiResponse(description = "Quando um Usuário Comum faz a requisição", responseCode = "403"),
         @ApiResponse(description = "Categoria ou Autor do Livro Não Encontrado", responseCode = "404"),
         @ApiResponse(description = "Algum Campo do Livro está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<BookDTO> insert(@RequestBody @Valid BookDTO dto);

    @Operation(
    description = "Atualizar Livro",
    summary = "Endpoint responsável por receber a requisição de Atualizar Livro",
    responses = {
         @ApiResponse(description = "Livro Atualizado", responseCode = "200"),
         @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
         @ApiResponse(description = "Quando um Usuário Comum faz a requisição", responseCode = "403"),
         @ApiResponse(description = "Categoria do Livro,Autor do Livro ou o Próprio Livro Não Encontrado", responseCode = "404"),
         @ApiResponse(description = "Algum Campo do Livro está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody @Valid BookDTO dto);

    @Operation(
    description = "Deletar Livro por Id",
    summary = "Endpoint responsável por receber a requisição de Deletar um Livro por Id",
    responses = {
         @ApiResponse(description = "Livro Deletado", responseCode = "204"),
         @ApiResponse(description = "Livro Não Encontrado", responseCode = "404")
   		}
	)
    ResponseEntity<BookDTO> delete(@PathVariable Long id);
}
