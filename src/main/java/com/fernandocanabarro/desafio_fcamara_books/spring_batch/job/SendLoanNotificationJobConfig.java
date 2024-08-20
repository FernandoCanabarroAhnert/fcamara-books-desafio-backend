package com.fernandocanabarro.desafio_fcamara_books.spring_batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendLoanNotificationJobConfig {

    @Bean
    public Job sendLoanNotificationJob(JobRepository jobRepository, Step sendEmailUserStep){
        return new JobBuilder("sendLoanNotificationJob", jobRepository)
            .start(sendEmailUserStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }
}
