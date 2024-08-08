package it.interno.controller;

/**
 * @author mirco.cennamo on 05/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
import it.interno.domain.JobParameters;
import it.interno.domain.ResponseDto;
import it.interno.service.JobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private JobService jobService;

    @PostMapping("/start")
    public ResponseEntity<ResponseDto<Long>> startJob(@RequestBody JobParameters jobParameters) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException,
            JobRestartException {
        Long jobId = jobService.deleteApplicationJob(jobParameters);

        return ResponseEntity.ok(ResponseDto.<Long>builder()
                .code(HttpStatus.OK.value())
                .body(jobId)
                .build());

    }


    @GetMapping
    public ResponseEntity<List> getAvailableRequests(){

        List<JobParameters> fileJobs = jobService.getAllJobs();
        return ResponseEntity.ok(fileJobs);
    }


}
