package com.fernandocanabarro.desafio_fcamara_books.services;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.EmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SpringTemplateEngine templateEngine;
    private final SendGrid sendGrid;

    @Async
    public void sendEmail(Mail mail){
        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            if (response.getStatusCode() >= 400) {
                throw new EmailException("Falha ao enviar Email");
            }
        }
        catch (IOException e){
            throw new EmailException(e.getMessage());
        }
    }

    public Mail createConfirmationEmailTemplate(String emailTo,String username, String activationCode, String subject){
        Map<String,Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        String template = templateEngine.process("email_confirmation", context);

        Email from = new Email("ahnertfernando499@gmail.com", "Biblioteca FCamara");
        Email to = new Email(emailTo);
        Content content = new Content("text/html", template);

        Mail mail = new Mail(from, subject, to, content);
        return mail;
    }

    public Mail createPasswordRecoverEmail(String emailTo,String username, String token, String subject){
        Map<String,Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("password_recover_token", token);

        Context context = new Context();
        context.setVariables(properties);

        String template = templateEngine.process("password_recover", context);

        Email from = new Email("ahnertfernando499@gmail.com", "Biblioteca FCamara");
        Email to = new Email(emailTo);
        Content content = new Content("text/html", template);

        Mail mail = new Mail(from, subject, to, content);
        return mail;
    }
}
