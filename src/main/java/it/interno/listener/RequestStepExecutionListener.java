package it.interno.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestStepExecutionListener implements StepExecutionListener {


    @Override
    public void beforeStep(org.springframework.batch.core.StepExecution stepExecution) {
        log.info("RequestStepExecutionListener Step started at: " + stepExecution.getStartTime());
        // Add any setup or logic before the job starts
    }

    @Override
    public ExitStatus afterStep(org.springframework.batch.core.StepExecution stepExecution) {
        log.info("RequestStepExecutionListener Step finished at: " + stepExecution.getEndTime());
        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("RequestStepExecutionListener STEP FINISHED! Time to verify the results");
        }
        return null;
    }
}