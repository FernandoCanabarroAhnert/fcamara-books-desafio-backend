package com.fernandocanabarro.desafio_fcamara_books.spring_batch.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Configuration
public class SendLoanNotificationScheduleJob extends QuartzJobBean{

    @Autowired
    private Job job;
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobLauncher jobLauncher;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer).getNextJobParameters(job)
            .toJobParameters();
        try{
            jobLauncher.run(job, jobParameters);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
