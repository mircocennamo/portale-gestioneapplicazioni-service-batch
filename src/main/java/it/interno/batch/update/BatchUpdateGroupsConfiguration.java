package it.interno.batch.update;

import it.interno.client.OimClient;
import it.interno.entity.Groups;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
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
public class BatchUpdateGroupsConfiguration {

    @Autowired
    RequestRepository requestRepository;
    //@Autowired
    OimClient oimClient;





    public static final String JOB_UPDATE_ALL_GROUPS_BATCH = "JOB_UPDATE_ALL_GROUPS_BATCH";


    @Bean(name = JOB_UPDATE_ALL_GROUPS_BATCH)
    public Job updateAllGroups(JobRepository jobRepository, JobCompletionNotificationListener JobCompletionNotificationListener,
                               Step stepRequestUpdateGroups) {
        return new JobBuilder("updateAllGroups", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(JobCompletionNotificationListener)
                .start(stepRequestUpdateGroups)
               .build();
    }





    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Request> readerUpdateGroups(@Value(("#{jobParameters['requestId']}")) String requestId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByIdRequestAndOperation")
                .arguments(Arrays.asList(requestId, Operation.UPDATE_ALL_GROUPS.getOperation()))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }



    @Bean
    public Step stepRequestUpdateGroups(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepRequestUpdateGroups", jobRepository)
                .<Request, Request> chunk(20, transactionManager)
                .reader(readerUpdateGroups(null)) //legge la riga dal db da lavorare
                .listener(new RequestStepExecutionListener())
                .listener(new RequestItemReadListener())
                .listener(new RequestItemProcessListener())
                .listener(new RequestItemWriteListener())
                .processor(requestUpdateItemProcessor(null,null,null)) //chiama oim e valorizza i campi della modifica
                .writer(chunk -> {
                    //do nothing
                })
                // .taskExecutor(taskExecutor)
                .build();
    }



    @Bean
    @StepScope
    public RequestUpdateItemProcessor requestUpdateItemProcessor(@Value(("#{jobParameters['utente']}")) String utenteModifica,
                                              @Value(("#{jobParameters['ufficio']}")) String ufficioModifica, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new RequestUpdateItemProcessor(oimClient,utenteModifica,ufficioModifica,currentTimeStamp);
    }

}
