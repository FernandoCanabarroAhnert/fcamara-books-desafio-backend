package com.fernandocanabarro.desafio_fcamara_books.spring_batch.step.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fernandocanabarro.desafio_fcamara_books.services.EmailService;
import com.sendgrid.helpers.mail.Mail;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SendEmailRequestReturnWriterConfig {

    private final EmailService emailService;

    @Bean
    public ItemWriter<Mail> sendEmailRequestReturnWriter(){
        return items -> items.forEach(item -> emailService.sendEmail(item));
    }
}
