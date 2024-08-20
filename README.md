# Desafio Backend: Sistema de Aluguel de Livros 📖

![Java](https://img.shields.io/badge/java-FF5722.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-F57F17?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![PhpMyAdmin](https://img.shields.io/badge/PhpMyAdmin-6f42c1?style=for-the-badge&logo=phpmyadmin&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![SendGrid](https://img.shields.io/badge/SendGrid-00BFFF?style=for-the-badge&logo=maildotru&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-F80000?style=for-the-badge&logo=openid&logoColor=white)
![Spring Batch](https://img.shields.io/badge/Spring%20Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Quartz](https://img.shields.io/badge/Quartz-2A2E33?style=for-the-badge&logo=clockify&logoColor=white)

## Modelo de Domínio
![FCamara-Classes](https://github.com/user-attachments/assets/3beda49b-b8b7-405c-bc5c-7199acfb4453)

## O que é o desafio? 🤔

O desafio, encontrado no GitHub da FCamara, é do funcionamento de um sistema de aluguel de livros, que permite o cadastro de usuários, o CRUD de Livros e de Cópias.

Como um diferencial para este desafio, eu utilizei o OAuth2 e JWT para a Autenticação, e utilizei o Spring Batch, que é um componente do Spring Framework utilizado para processar dados em lote.

Neste projeto, o Spring Batch foi utilizado para ler do Banco de Dados os usuários com aluguéis ativos que estivessem faltando um dia para serem retornados. Após a leitura, seria processado um email, com os dados do usuário, da cópia e
do aluguel em si. Depois do processamento, os emails são enviados, utilizando o serviço do SendGrid.

E para fazer com que este Job funcionasse como um serviço, eu utilizei o Quartz, que é um dependência relacionada ao Spring Batch, que é utilizado para agendar horários para a execução do Job (que, neste caso, foi configurado para executar
todos os dias às 14 horas).

O desafio pode ser encontrado aqui: <https://github.com/fcamarasantos/node-react-test>

<p align="left" width="100%">
    <img width="25%" src="https://github.com/user-attachments/assets/b00c2e0c-8a99-4ece-a6f7-3054f1aed505"> 
</p>

## Requisitos da Aplicação ✅

Esses foram os requisitos definidos no enunciado original:

Criar um sistema de controle de aluguel livros em uma biblioteca. 
Dados: 
- um livro pode ter varias copias
- uma copia não pode estar com mais de uma pessoa ao mesmo tempo

Todos os campos são obrigatórios.
### Cadastro de Pessoa
- Nome
- CPF
- Data Nascimento
- Endereço completo

### Cadastro de Livro
- Titulo
- Autor
- ISBN
- Código da cópia

### Funcionalidades
- Pessoa: CRUD
- Livro: CRUD
- Copia: CRUD
- Controle de aluguel

### Requisitos
- Persistencia utlizando banco de dados relacional open source (MySQL, Postgresql, Firebird, etc..) de sua preferencia
- RESTful JSON
- Autenticacao JWT
- Se pessoa atrasou devolução mais de 2x ela não pode alugar mais
- Criar README do projeto descrevendo as tecnologias utilizadas, chamadas dos serviços e configurações necessário para executar a aplicação

### Serviço RESTful 🚀

* Desenvolvimento de um serviço RESTful para toda a aplicação.

## Tecnologias 💻
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT](https://jwt.io/)
- [OAuth2](https://oauth.net/2/)
- [Spring Batch](https://spring.io/projects/spring-batch)
- [Quartz](https://www.quartz-scheduler.org/)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [H2](https://www.baeldung.com/spring-boot-h2-database)
- [MySQL](https://www.mysql.com/)
- [PhpMyAdmin](https://www.phpmyadmin.net/)
- [Docker](https://www.docker.com/)
- [SendGrid](https://sendgrid.com/en-us)
- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [MockMvc](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html)
- [RestAssured](https://rest-assured.io/)
- [Jacoco](https://www.eclemma.org/jacoco/)
- [Bean Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
- [HATEOAS](https://spring.io/projects/spring-hateoas)

## Práticas adotadas ✨

- SOLID, DRY, YAGNI, KISS
- API REST
- TDD
- Consultas com Spring Data JPA
- Injeção de Dependências
- Testes Automatizados
- Geração automática do Swagger com a OpenAPI 3
- Autenticação e Autorização com OAuth2 e JWT

## Diferenciais 🔥

Alguns diferenciais que não foram solicitados no desafio:

* Utilização de Docker
* Spring Batch, para enviar email aos usuários com aluguéis ativos que devem ser retornados no dia seguinte
* Quartz, para agendar o envio de emails do Spring Batch
* Cadastro de Usuário em 2 etapas
* Recuperação de Senha
* SendGrid para envio de emails
* TDD-Test Driven Development
* Tratamento de exceções
* Validações com Constraints Customizados
* Testes Unitários e de Integração
* Cobertura de Testes com Jacoco
* Documentação Swagger
* Consumo da API da ViaCep com o RestTemplate, para consultar o CEP do Usuário
* Implementação de HATEOAS

## Como executar 🎉

1.Clonar repositório git:

```text
git clone https://github.com/FernandoCanabarroAhnert/desafio-fcamara-books.git
```

2.Instalar dependências.

```text
mvn clean install
```

3.Executar a aplicação Spring Boot.

4.Testar endpoints através do Postman ou da url
<http://localhost:8080/swagger-ui/index.html#/>

### Usando Docker 🐳

- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Construir a imagem:
```
./mvnw spring-boot:build-image
```
- Executar o container:
```
docker run --name desafio-fcamara-books -p 8080:8080  -d desafio-fcamara-books:0.0.1-SNAPSHOT
```
## API Endpoints 📚

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/):
- Collection do Postman completa: [Postman-Collection](https://github.com/user-attachments/files/16680507/Book.FCamara.postman_collection.json)

- Registrar Usuário
```
$ http POST http://localhost:8080/users/register

-entrada

{
    "fullName": "Fernando",
    "cpf": "448.157.471-21",
    "email": "fernando@gmail.com",
    "password": "12345Az@",
    "birthDate": "28/10/2005",
    "address":{
        "cep":"91349900",
        "numero": "1800",
        "complemento": "Casa Amarela"
    }
}

-saída

{
    "id": 3,
    "fullName": "Fernando",
    "cpf": "448.157.471-21",
    "email": "fernando@gmail.com",
    "birthDate": "28/10/2005",
    "address":{
        "id": 3
        "cep":"91349900",
        "logradouro": "Avenida João Wallig",
        "numero": "1800",
        "complemento": "Casa Amarela"
        "bairro": "Passo da Areia",
        "cidade": "Porto Alegre",
        "estado": "RS"
    }
    "roles": [
        {
            "id": 2,
            "authority": "ROLE_USER"
        }
    ]
}
```

- Efetuar Login
```
$ http POST http://localhost:8080/users/login

{
    "email": "ahnertfernando@gmail.com",
    "password": "12345Az@"
}
```

- Inserir Livro
```
$ http POST http://localhost:8080/books

-entrada

{
    "title": "Arquitetura Limpa",
    "description": "Arquitetura Limpa",
    "author": {
        "id": 1
    },
    "pages": "123",
    "isbn": "123456789",
    "categories": [
            {
                "id":1
            }
    ]
}

-saida

{
    "title": "Arquitetura Limpa",
    "description": "Arquitetura Limpa",
    "author": {
        "id": 1,
        "name": "George Orwell"
    },
    "pages": "123",
    "isbn": "123456789",
    "categories": [
            {
                "id":1,
                "name": "Fiction"
            }
    ]
}
```

- Realizar Alguel
```
$ http POST http://localhost:8080/loans

{
    "copyId": 1
}

```


