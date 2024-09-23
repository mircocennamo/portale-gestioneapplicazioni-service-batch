package it.interno.batch.delete;

import it.interno.entity.Request;
import it.interno.enumeration.Operation;
import it.interno.enumeration.Status;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.request.RequestStepExecutionListener;
import it.interno.listener.request.RequestItemProcessListener;
import it.interno.listener.request.RequestItemReadListener;
import it.interno.listener.request.RequestItemWriteListener;
import it.interno.processor.RequestItemProcessor;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BatchDeleteGroupsConfiguration {

    @Autowired
    RequestRepository requestRepository;






    public static final String JOB_DELETE_ALL_GROUPS_BATCH = "JOB_DELETE_ALL_GROUPS_BATCH";


    @Bean(name = JOB_DELETE_ALL_GROUPS_BATCH)
    public Job deleteApplication(JobRepository jobRepository, JobCompletionNotificationListener JobCompletionNotificationListener, Step stepRequestDeleteGroups,
                                 Step stepDeleteRuoliOim,Step stepRimozioneRuoloAUtenteOim,Step stepGroups
                                 ,Step stepRegoleSicurezza,Step stepApplicMotivMember) {
        return new JobBuilder("deleteAllGroupsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(JobCompletionNotificationListener)
                .start(stepRequestDeleteGroups)
                .next(stepDeleteRuoliOim) //groups
                .next(stepRimozioneRuoloAUtenteOim)
                .next(stepGroups)
                .next(stepRegoleSicurezza)
                .next(stepApplicMotivMember)
                .build();
    }





    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Request> readerDeleteGroups(@Value(("#{jobParameters['requestId']}")) String requestId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByIdRequestAndOperation")
                .arguments(Arrays.asList(requestId, Operation.DELETE_ALL_GROUPS.getOperation()))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }



    @Bean
    public Step stepRequestDeleteGroups(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            RepositoryItemWriter<Request> writer) {
        return new StepBuilder("stepRequestDeleteGroups", jobRepository)
                .<Request, Request> chunk(20, transactionManager)
                .reader(readerDeleteGroups(null)) //legge la riga dal db da lavorare
                .listener(new RequestStepExecutionListener())
                .listener(new RequestItemReadListener())
                .listener(new RequestItemProcessListener())
                .listener(new RequestItemWriteListener())
                .processor(new RequestItemProcessor())
                .writer(writer)
                // .taskExecutor(taskExecutor)
                .build();
    }

}
