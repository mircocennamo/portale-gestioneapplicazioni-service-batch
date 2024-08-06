package it.interno.service;

import it.interno.domain.JobParameters;

import java.util.List;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public interface JobService {

    void deleteApplicationJob(JobParameters jobParameters);

    List<JobParameters> getAllJobs();
}
