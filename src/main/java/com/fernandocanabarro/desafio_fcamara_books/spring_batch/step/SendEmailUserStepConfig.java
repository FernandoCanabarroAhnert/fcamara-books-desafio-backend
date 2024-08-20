package com.fernandocanabarro.desafio_fcamara_books.spring_batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.sendgrid.helpers.mail.Mail;

@Configuration
public class SendEmailUserStepConfig {

    @Autowired
    @Qualifier("appTransactionManager")
    private PlatformTransactionManager appTransactionManager;

    @Bean
    public Step sendEmailUserStep(JobRepository jobRepository, ItemReader<Loan> readUsersWithLoansCloseToReturnReader,
                ItemProcessor<Loan,Mail> processLoanNotificationEmailProcessor, ItemWriter<Mail> sendEmailRequestReturnWriter){
        return new StepBuilder("sendEmailUserStep", jobRepository)
                    .<Loan,Mail>chunk(1, appTransactionManager)
                    .reader(readUsersWithLoansCloseToReturnReader)
                    .processor(processLoanNotificationEmailProcessor)
                    .writer(sendEmailRequestReturnWriter)
                    .build();
    }
}
