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

import java.text.ParseException;
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
    @Qualifier("JOB_DELETE_ALL_MOTIVAZIONI_BATCH")
    private Job batchDeleteAllMotivazioniJob;


    @Autowired
    @Qualifier("JOB_UPDATE_ALL_GROUPS_BATCH")
    private Job batchUpdateAllGroupsJob;

    @Autowired
    @Qualifier("JOB_UPDATE_ALL_REGOLE_SICUREZZA_BATCH")
    private Job batchUpdateAllRegoleSicurezzaJob;

    @Autowired
    RequestRepository requestRepository;


    private static final String REQUEST_ID = "requestId";

    private static final String APPLICATION_ID = "applicationId";

    private static final String UTENTE = "utente";

    private static final String UFFICIO = "ufficio";

    private static final String CURRENT_TIMESTAMP = "currentTimeStamp";

    private static final String NOME_RUOLO = "nomeRuolo";

    private static final String TIPO_MOTIVAZIONE_ID = "tipoMotivazioneId";


    private final AtomicLong incrementer = new AtomicLong();


    ExecutorService executorService = Executors.newFixedThreadPool(10);




    //@Scheduled(fixedRate = 5000)
    public  Long  deleteApplicationJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(REQUEST_ID,jobParameters.getRequestId())
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE,jobParameters.getUtente())
                .addString(UFFICIO,jobParameters.getUfficio())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getTimeStamp(jobParameters.getCurrentDate()));
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
    public Long deleteAllGroupsJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(REQUEST_ID,jobParameters.getRequestId())
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE,jobParameters.getUtente())
                .addString(UFFICIO,jobParameters.getUfficio())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getTimeStamp(jobParameters.getCurrentDate()));
        JobExecution jobExecution = jobLauncher.run(batchDeleteAllGroupsJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }

    @Override
    public Long deleteAllRulesJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(REQUEST_ID,jobParameters.getRequestId())
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE,jobParameters.getUtente())
                .addString(UFFICIO,jobParameters.getUfficio())
                .addString(NOME_RUOLO,jobParameters.getNomeRuolo())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getTimeStamp(jobParameters.getCurrentDate()));
        JobExecution jobExecution = jobLauncher.run(batchDeleteAllRulesJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }

    @Override
    public Long deleteAllMotivazioniJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(REQUEST_ID,jobParameters.getRequestId())
                .addString(APPLICATION_ID,jobParameters.getApplicationId())
                .addString(UTENTE,jobParameters.getUtente())
                .addString(UFFICIO,jobParameters.getUfficio())
                .addString(TIPO_MOTIVAZIONE_ID,jobParameters.getTipoMotivazioneId())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getTimeStamp(jobParameters.getCurrentDate()));
        JobExecution jobExecution = jobLauncher.run(batchDeleteAllMotivazioniJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }


    @Override
    public Long updateAllGroupsJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(REQUEST_ID,jobParameters.getRequestId())
                .addString(UTENTE,jobParameters.getUtente())
                .addString(UFFICIO,jobParameters.getUfficio())
                 .addDate(CURRENT_TIMESTAMP, ConversionUtils.getTimeStamp(jobParameters.getCurrentDate()));
        JobExecution jobExecution = jobLauncher.run(batchUpdateAllGroupsJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }

    @Override
    public Long updateAllRegoleSicurezzaJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, ParseException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(REQUEST_ID,jobParameters.getRequestId())
                .addString(UTENTE,jobParameters.getUtente())
                .addString(UFFICIO,jobParameters.getUfficio())
                .addDate(CURRENT_TIMESTAMP, ConversionUtils.getTimeStamp(jobParameters.getCurrentDate()));
        JobExecution jobExecution = jobLauncher.run(batchUpdateAllRegoleSicurezzaJob, jobParametersBuilder.toJobParameters());

        return jobExecution.getJobId();
    }


}
