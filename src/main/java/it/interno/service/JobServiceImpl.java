package it.interno.service;

import it.interno.domain.JobParameters;
import it.interno.entity.Request;
import it.interno.repository.RequestRepository;
import it.interno.utils.ConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    @Qualifier("myJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    private Job batchJob;

    @Autowired
    RequestRepository requestRepository;


    private static String APPLICATION_ID = "applicationId";

    private static String UTENTE_CANCELLAZIONE = "utenteCancellazione";

    private static String UFFICIO_CANCELLAZIONE = "ufficioCancellazione";

    private static String CURRENT_TIMESTAMP = "currentTimeStamp";


    private final AtomicLong incrementer = new AtomicLong();


    ExecutorService executorService = Executors.newFixedThreadPool(10);



    public  Long  deleteApplicationJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE_CANCELLAZIONE,jobParameters.getUtenteCancellazione())
                .addString(UFFICIO_CANCELLAZIONE,jobParameters.getUfficioCancellazione())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getCurrentTimestamp());
        JobExecution jobExecution = jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();

    }

    @Override
    public List<JobParameters> getAllJobs() {
        List<Request> requestList = requestRepository.findRequestByStatus("1");
        List<JobParameters> parameters = new ArrayList<>();
        requestList.stream().forEach(request -> {
            JobParameters jobParameters = new JobParameters();
            jobParameters.setApplicationId(request.getIdApplicazione());
            parameters.add(jobParameters);
        });



        return parameters;
    }


}
