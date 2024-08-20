package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.EmailException;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;

import java.util.Map;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Mock
    private SendGrid sendGrid;

    @Test
    public void sendEmailShouldThrowNoExceptionWhenDataIsValid() throws IOException {
        Mail mail = new Mail();
        Response response = new Response();
        response.setStatusCode(200);

        when(sendGrid.api(any(Request.class))).thenReturn(response);

        emailService.sendEmail(mail);

        verify(sendGrid).api(any(Request.class));
    }

    @Test
    public void sendEmailShouldThrowEmailExceptionWhenStatusCodeIs400() throws IOException {
        Mail mail = new Mail();
        Response response = new Response();
        response.setStatusCode(400);

        when(sendGrid.api(any(Request.class))).thenReturn(response);

        assertThrows(EmailException.class, () -> {
            emailService.sendEmail(mail);
        });
    }

    @Test
    public void sendEmailShouldThrowEmailExceptionWhenIOExceptionIsThrown() throws IOException {
        Mail mail = new Mail();

        when(sendGrid.api(any(Request.class))).thenThrow(new IOException("IO Error"));

        assertThrows(EmailException.class, () -> {
            emailService.sendEmail(mail);
        });
    }

    @Test
    public void testCreateConfirmationEmailTemplate() {
        String emailTo = "test@example.com";
        String username = "user";
        String activationCode = "12345";
        String subject = "Confirmation";

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        when(templateEngine.process(eq("email_confirmation"), any(Context.class)))
                .thenReturn("<html>Email Content</html>");

        Mail mail = emailService.createConfirmationEmailTemplate(emailTo, username, activationCode, subject);

        assertEquals("ahnertfernando499@gmail.com", mail.getFrom().getEmail());
        assertEquals(emailTo, mail.getPersonalization().get(0).getTos().get(0).getEmail());
        assertEquals("<html>Email Content</html>", mail.getContent().get(0).getValue());
    }

    @Test
    public void testCreatePasswordRecoverEmail() {
        String emailTo = "test@example.com";
        String username = "user";
        String token = "token";
        String subject = "Password Recovery";

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("password_recover_token", token);

        Context context = new Context();
        context.setVariables(properties);

        when(templateEngine.process(eq("password_recover"), any(Context.class)))
                .thenReturn("<html>Password Recover Email</html>");

        Mail mail = emailService.createPasswordRecoverEmail(emailTo, username, token, subject);

        assertEquals("ahnertfernando499@gmail.com", mail.getFrom().getEmail());
        assertEquals(emailTo, mail.getPersonalization().get(0).getTos().get(0).getEmail());
        assertEquals("<html>Password Recover Email</html>", mail.getContent().get(0).getValue());
    }

}
