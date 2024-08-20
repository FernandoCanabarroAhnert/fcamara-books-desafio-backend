package com.fernandocanabarro.desafio_fcamara_books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Configuration
@EnableJpaRepositories(
    basePackages = "com.fernandocanabarro.desafio_fcamara_books.repositories",
    entityManagerFactoryRef = "appEntityManagerFactory",
    transactionManagerRef = "appTransactionManager"
)
public class DesafioFcamaraBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioFcamaraBooksApplication.class, args);
	}

}
