package com.fernandocanabarro.desafio_fcamara_books.spring_batch.step.processor;

import java.time.Duration;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Configuration
public class ProcessLoanNotificationEmailProcessorConfig {

    @Bean
    public ItemProcessor<Loan,Mail> processLoanNotificationEmailProcessor(){
        return new ItemProcessor<Loan,Mail>() {

            @Override
            public Mail process(Loan loan) throws Exception {
                Email from = new Email("ahnertfernando499@gmail.com", "Biblioteca FCamara");
                Email to = new Email(loan.getUser().getEmail());
                Content content = new Content("text/plain", generateEmailText(loan));
                Mail mail = new Mail(from, "Notificação de Devolução de Livro", to, content);
                Thread.sleep(Duration.ofSeconds(1L));
                return mail;
            }
            
        };
    }

    protected String generateEmailText(Loan loan) {
        StringBuilder writer = new StringBuilder();
        writer.append(String.format("Prezado(a), %s, matricula %d\n", loan.getUser().getFullName(), loan.getUser().getId()));
        writer.append(String.format("Informamos que o prazo de devolução do livro %s é amanhã (%s) \n", loan.getCopy().getBook().getTitle(), loan.getDateToReturn()));
        writer.append("Solicitamos que você renove o livro ou devolva, assim que possível.\n");
        writer.append("A Biblioteca FCamara está funcionando de segunda a sexta, das 9h às 17h.\n\n");
        writer.append("Atenciosamente,\n");
        writer.append("Setor de empréstimo e devolução\n");
        writer.append("BIBLIOTECA FCAMARA");
        return writer.toString();
    }
}
