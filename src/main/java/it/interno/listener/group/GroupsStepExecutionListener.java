package it.interno.listener.group;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GroupsStepExecutionListener implements StepExecutionListener {


    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("GroupsStepExecutionListener Step started at: " + stepExecution.getStartTime());
        // Add any setup or logic before the job starts
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("GroupsStepExecutionListener Step finished at: " + stepExecution.getEndTime());
        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! GroupsStepExecutionListener STEP FINISHED! Time to verify the results");
        }
        return null;
    }
}