package it.interno.service;

import it.interno.domain.JobParameters;
import it.interno.entity.Request;
import it.interno.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job batchJob;

    @Autowired
    RequestRepository requestRepository;



    public  void deleteApplicationJob(JobParameters jobParameters){
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString("applicationId",jobParameters.getApplicationId())
                .addString("utenteCancellazione",jobParameters.getUtenteCancellazione())
                .addString("ufficioCancellazione",jobParameters.getUfficioCancellazione())
                .addDate("currentTimeStamp", getCurrentTimestamp());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try{

                    JobExecution jobExecution = jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());
                    log.info("batch Loaded Successfully");

                }
                catch (Exception e){
                    log.error(e.getMessage(), (Object[]) e.getStackTrace());

                }
            }
        });

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

    static Timestamp getCurrentTimestamp(){
        ZoneId fusoOrario = ZoneId.of("Europe/Rome");
        return Timestamp.valueOf(LocalDateTime.now(fusoOrario));
    }
}
