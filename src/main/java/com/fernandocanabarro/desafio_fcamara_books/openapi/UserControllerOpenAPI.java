package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AccountActivationResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.EmailDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface UserControllerOpenAPI {

    @Operation(
    description = "Registrar Usuário",
    summary = "Endpoint responsável por receber a requisição de Registrar Usuário",
    responses = {
         @ApiResponse(description = "Usuário Registrado", responseCode = "201"),
         @ApiResponse(description = "CEP do Usuário Não Encontrado", responseCode = "400"),
         @ApiResponse(description = "Algum Campo do Usuário está Inválido, ou o Email ou o CPF já existem", responseCode = "422")
   		}
	)
    ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationRequestDTO dto);

    @Operation(
    description = "Ativar Conta",
    summary = "Endpoint responsável por receber a requisição de Ativar Conta",
    responses = {
         @ApiResponse(description = "Conta Ativada", responseCode = "200"),
         @ApiResponse(description = "Quando o Código existe mas já expirou", responseCode = "400"),
         @ApiResponse(description = "Código Não Encontrado", responseCode = "404")
        }
	)
   ResponseEntity<AccountActivationResponseDTO> activate(@RequestParam(name = "code",defaultValue = "") String code);

    @Operation(
    description = "Efetuar Login",
    summary = "Endpoint responsável por receber a requisição de Efetuar Login",
    responses = {
        @ApiResponse(description = "Login Efetuado", responseCode = "200"),
         @ApiResponse(description = "Credenciais Inválidas", responseCode = "401"),
         @ApiResponse(description = "Conta Não Ativada", responseCode = "403")
   		}
	)
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto);

    @Operation(
    description = "Envio de Email para Recuperação de Senha",
    summary = "Endpoint responsável por receber a requisição de Recuperação de Senha",
    responses = {
        @ApiResponse(description = "Código Enviado", responseCode = "200"),
         @ApiResponse(description = "Email do Usuário não Existe", responseCode = "404")
   		}
	)
    ResponseEntity<Void> createRecoverToken(@RequestBody @Valid EmailDTO obj);

    @Operation(
    description = "Recuperar Senha",
    summary = "Endpoint responsável por receber a requisição de Mudar Senha",
    responses = {
         @ApiResponse(description = "Senha do Usuário Atualizada", responseCode = "200"),
         @ApiResponse(description = "Token não Existe", responseCode = "404"),
         @ApiResponse(description = "Algum Campo da Requisição está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<Void> saveNewPassword(@RequestBody @Valid NewPasswordDTO obj);

    @Operation(
    description = "Consultar minhas Informações",
    summary = "Endpoint responsável por receber a requisição de Consultar meus Dados",
    responses = {
        @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "Usuário não logado faz a Requisição", responseCode = "401")
   		}
	)
    ResponseEntity<UserDTO> getMe();
}
