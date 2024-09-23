package it.interno.batch.update;


import it.interno.entity.Request;
import it.interno.enumeration.Operation;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.request.RequestItemProcessListener;
import it.interno.listener.request.RequestItemReadListener;
import it.interno.listener.request.RequestItemWriteListener;
import it.interno.listener.request.RequestStepExecutionListener;
import it.interno.repository.RequestRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BatchUpdateRegoleSicurezzaConfiguration {

    @Autowired
    RequestRepository requestRepository;






    public static final String JOB_UPDATE_ALL_REGOLE_SICUREZZA_BATCH = "JOB_UPDATE_ALL_REGOLE_SICUREZZA_BATCH";


    @Bean(name = JOB_UPDATE_ALL_REGOLE_SICUREZZA_BATCH)
    public Job updateAllRegoleSicurezza(JobRepository jobRepository, JobCompletionNotificationListener JobCompletionNotificationListener,
                                        Step stepRequestUpdateRegoleSicurezza,
                                        Step stepRegoleByNomeRuoloAndAppId,Step stepGroupMemberGetByRuolo,
                                        Step stepGroupMemberUtentiDistintiByApp) {
        return new JobBuilder("updateAllRegoleSicurezza", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(JobCompletionNotificationListener)
                .start(stepRequestUpdateRegoleSicurezza)
                //check validita
                .next(stepRegoleByNomeRuoloAndAppId)
                .next(stepGroupMemberGetByRuolo)
                .next(stepGroupMemberUtentiDistintiByApp)
                //Fine check validita
               .build();
    }





    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Request> readerRequestUpdateRegoleSicurezza(@Value(("#{jobParameters['requestId']}")) String requestId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByIdRequestAndOperation")
                .arguments(Arrays.asList(requestId, Operation.UPDATE_ALL_REGOLE_SICUREZZA.getOperation()))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }



    @Bean
    public Step stepRequestUpdateRegoleSicurezza(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepRequestUpdateRegoleSicurezza", jobRepository)
                .<Request, Request> chunk(20, transactionManager)
                .reader(readerRequestUpdateRegoleSicurezza(null)) //legge la riga dal db da lavorare
                .listener(new RequestStepExecutionListener())
                .listener(new RequestItemReadListener())
                .listener(new RequestItemProcessListener())
                .listener(new RequestItemWriteListener())
                .processor(requestUpdateRegoleSicurezzaItemProcessor(null,null,null)) //chiama oim e valorizza i campi della modifica
                .writer(chunk -> {
                    //do nothing
                })
                // .taskExecutor(taskExecutor)
                .build();
    }



    @Bean
    @StepScope
    public RequestUpdateRegoleSicurezzaItemProcessor requestUpdateRegoleSicurezzaItemProcessor(@Value(("#{jobParameters['utente']}")) String utenteModifica,
                                              @Value(("#{jobParameters['ufficio']}")) String ufficioModifica, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new RequestUpdateRegoleSicurezzaItemProcessor(utenteModifica,ufficioModifica,currentTimeStamp);
    }

}
