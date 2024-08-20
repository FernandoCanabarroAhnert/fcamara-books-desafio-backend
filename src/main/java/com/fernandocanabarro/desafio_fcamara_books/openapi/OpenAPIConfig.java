package com.fernandocanabarro.desafio_fcamara_books.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {

     @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Desafio Backend FCamara")
                .version("FernandoCanabarroAhnert")
                .description("Este é um projeto baseado no desafio proposto pela FCamara para o gerenciamento de Usuários, Livros e Cópias, em que os Usuários podem se Cadastrar e Logar, bem como Alugar Cópias de Livros"))
                .externalDocs(new ExternalDocumentation()
                        .description("Link Github do Desafio proposto")
                        .url("https://github.com/fcamarasantos/node-react-test"));
    }
}
