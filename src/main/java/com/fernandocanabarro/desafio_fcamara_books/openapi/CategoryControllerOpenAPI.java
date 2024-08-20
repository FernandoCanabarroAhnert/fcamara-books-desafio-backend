package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.fernandocanabarro.desafio_fcamara_books.dtos.BookMinDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CategoryDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface CategoryControllerOpenAPI {

    @Operation(
    description = "Consultar Categorias",
    summary = "Endpoint responsável por receber a requisição da Consulta de Categorias",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable);

    @Operation(
    description = "Consultar Livros de uma dada Categoria",
    summary = "Endpoint responsável por receber a requisição da Consulta de Livro por Categoria",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "Categoria Não Encontrado", responseCode = "404")
   		}
	)
    ResponseEntity<Page<BookMinDTO>> findAllBooksByCategory(@PathVariable Long id, Pageable pageable);
}
