package it.interno.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);




    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job started at: " + jobExecution.getStartTime());
        // Add any setup or logic before the job starts
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Job finished at: " + jobExecution.getEndTime());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");

            //String query = "SELECT brand, origin, characteristics FROM coffee";
            //jdbcTemplate.query(query, (rs, row) -> new Coffee(rs.getString(1), rs.getString(2), rs.getString(3)))
            //    .forEach(coffee -> LOGGER.info("Found < {} > in the database.", coffee));
        }
    }
}
