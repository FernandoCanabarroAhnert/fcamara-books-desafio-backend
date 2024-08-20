package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanReturnResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.MyLoansDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface LoanControllerOpenAPI {

    @Operation(
        description = "Realizar Aluguel",
        summary = "Endpoint responsável por receber a requisição de Realizar Aluguel",
        responses = {
             @ApiResponse(description = "Aluguel Criado", responseCode = "201"),
             @ApiResponse(description = "Quando um Usuário já excedeu o limite de atrasos ou já possui uma cópia do livro em mãos", responseCode = "400"),
             @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
             @ApiResponse(description = "Algum Campo do Livro está Inválido", responseCode = "422")
               }
        )
    ResponseEntity<LoanResponseDTO> makeLoan(@RequestBody @Valid LoanRequestDTO dto);

    @Operation(
    description = "Consultar meus Aluguéis",
    summary = "Endpoint responsável por receber a requisição de Consultar meu Aluguéis",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    ResponseEntity<Page<MyLoansDTO>> findMyLoans(Pageable pageable);

    @Operation(
    description = "Retornar Cópia",
    summary = "Endpoint responsável por receber a requisição de Retornar Cópia",
    responses = {
         @ApiResponse(description = "Livro Retornado", responseCode = "200"),
         @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
         @ApiResponse(description = "A Cópia não Existe ou o Usuário não tem um Aluguel Ativo com esta Cópia", responseCode = "404"),
         @ApiResponse(description = "Algum Campo do Livro está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<LoanReturnResponseDTO> returnLoan(@RequestBody @Valid LoanRequestDTO dto);

    @Operation(
    description = "Consultar todos os Aluguéis",
    summary = "Endpoint responsável por receber a requisição de Consultar todos os Aluguéis",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "Quando um Usuário não logado faz a requisição", responseCode = "401"),
         @ApiResponse(description = "Quando um Usuário Comum faz a requisição", responseCode = "403")
   		}
	)
    ResponseEntity<Page<LoanDTO>> findAllLoans(Pageable pageable);

}
