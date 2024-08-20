package com.fernandocanabarro.desafio_fcamara_books.spring_batch.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fernandocanabarro.desafio_fcamara_books.spring_batch.job.SendLoanNotificationScheduleJob;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail quartzJobDetail(){
        return JobBuilder.newJob(SendLoanNotificationScheduleJob.class).storeDurably().build();
    }

    @Bean
    public Trigger jobTrigger(){
        String exp = "0 00 14 * * ?";
        return TriggerBuilder
            .newTrigger()
            .forJob(quartzJobDetail())
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule(exp))
            .build();
    }
}
