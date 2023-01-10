package fr.hn.services.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("master")
@Component
@Slf4j
@EnableScheduling
public class BatchLauncher {
    @Autowired
    private Job job;
    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "* * * * * ?")
    public void perform() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("joId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        log.info("", job.getName());
        jobLauncher.run(job, jobParameters);
    }
}
