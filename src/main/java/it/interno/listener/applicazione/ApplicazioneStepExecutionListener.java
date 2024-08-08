package it.interno.listener.applicazione;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicazioneStepExecutionListener implements StepExecutionListener {


    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("ApplicazioneStepExecutionListener Step started at: " + stepExecution.getStartTime());
        // Add any setup or logic before the job starts
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("ApplicazioneStepExecutionListener Step finished at: " + stepExecution.getEndTime());
        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! ApplicazioneStepExecutionListener STEP FINISHED! Time to verify the results");
        }
        return null;
    }
}