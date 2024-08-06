package it.interno.controller;

/**
 * @author mirco.cennamo on 05/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
import it.interno.domain.JobParameters;
import it.interno.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private JobService jobService;

    @PostMapping("/start")
    public ResponseEntity startJob(@RequestBody JobParameters jobParameters){

        jobService.deleteApplicationJob(jobParameters);
        return ResponseEntity.ok("delete Application Job started");
    }


    @GetMapping
    public ResponseEntity<List> getAvailableRequests(){

        List<JobParameters> fileJobs = jobService.getAllJobs();
        return ResponseEntity.ok(fileJobs);
    }
}
