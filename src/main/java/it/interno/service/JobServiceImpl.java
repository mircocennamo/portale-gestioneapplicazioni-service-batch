package it.interno.service;

import it.interno.domain.JobParameters;
import it.interno.entity.Request;
import it.interno.enumeration.Operation;
import it.interno.enumeration.Status;
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
    @Qualifier("asyncJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("JOB_DELETE_APPLICATION_BATCH")
    private Job batchDeleteApplicationJob;

    @Autowired
    @Qualifier("JOB_DELETE_ALL_GROUPS_BATCH")
    private Job batchDeleteAllGroupsJob;

    @Autowired
    @Qualifier("JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH")
    private Job batchDeleteAllRulesJob;

    @Autowired
    RequestRepository requestRepository;


    private static String APPLICATION_ID = "applicationId";

    private static String UTENTE_CANCELLAZIONE = "utenteCancellazione";

    private static String UFFICIO_CANCELLAZIONE = "ufficioCancellazione";

    private static String CURRENT_TIMESTAMP = "currentTimeStamp";

    private static String NOME_RUOLO = "nomeRuolo";


    private final AtomicLong incrementer = new AtomicLong();


    ExecutorService executorService = Executors.newFixedThreadPool(10);




    //@Scheduled(fixedRate = 5000)
    public  Long  deleteApplicationJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE_CANCELLAZIONE,jobParameters.getUtenteCancellazione())
                .addString(UFFICIO_CANCELLAZIONE,jobParameters.getUfficioCancellazione())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getCurrentTimestamp());
        JobExecution jobExecution = jobLauncher.run(batchDeleteApplicationJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();

    }

    @Override
    public List<Request> getAllJobs(Status status, Operation operation) {
        if(status == null && operation == null)
            return requestRepository.findAll();
        else if (status == null) {
            return requestRepository.findRequestByOperation(operation.getOperation());
        } else if (operation == null) {
            return requestRepository.findRequestByStatus(status.getStatus());

        }
        return requestRepository.findRequestByStatusAndOperation(status.getStatus(),operation.getOperation());
    }

    @Override
    public Long deleteAllGroupsJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE_CANCELLAZIONE,jobParameters.getUtenteCancellazione())
                .addString(UFFICIO_CANCELLAZIONE,jobParameters.getUfficioCancellazione())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getCurrentTimestamp());
        JobExecution jobExecution = jobLauncher.run(batchDeleteAllGroupsJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }

    @Override
    public Long deleteAllRulesJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE_CANCELLAZIONE,jobParameters.getUtenteCancellazione())
                .addString(UFFICIO_CANCELLAZIONE,jobParameters.getUfficioCancellazione())
                .addString(NOME_RUOLO,jobParameters.getNomeRuolo())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getCurrentTimestamp());
        JobExecution jobExecution = jobLauncher.run(batchDeleteAllRulesJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }


}
