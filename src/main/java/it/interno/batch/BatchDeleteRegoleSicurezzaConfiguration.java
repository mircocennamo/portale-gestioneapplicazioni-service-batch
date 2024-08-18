package it.interno.batch;

import it.interno.entity.GroupMembers;
import it.interno.entity.Groups;
import it.interno.entity.Request;
import it.interno.enumeration.Operation;
import it.interno.enumeration.Status;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.RequestStepExecutionListener;
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
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
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
public class BatchDeleteRegoleSicurezzaConfiguration {

    @Autowired
    RequestRepository requestRepository;






    public static final String JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH = "JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH";
/*

    @Bean(name = JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH)
    public Job deleteAllRegoleSicurezza(JobRepository jobRepository, JobCompletionNotificationListener listener, Step stepRequestDeleteRegoleSicurezza,
                                 Step stepDeleteRegoleSicurezzaOim,Step stepApplicMotivMember,
                                 Step stepGroupMember,Step stepRegoleSicurezza,Step stepGroups) {
        return new JobBuilder("deleteAllRegoleSicurezza", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepRequestDeleteRegoleSicurezza).next(stepDeleteRegoleSicurezzaOim)
                .next(stepGroupMember).next(stepGroups)
                .next(stepRegoleSicurezza)
                .next(stepApplicMotivMember)
                .build();
    }





    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Request> readerDeleteRegoleSicurezza(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByStatusAndIdAppAndOperation")
                .arguments(Arrays.asList(Status.TO_BE_ASSIGNED.getStatus(),applicationId, Operation.DELETE_ALL_REGOLE_SICUREZZA.getOperation()))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }



    @Bean
    public Step stepRequestDeleteRegoleSicurezza(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            RepositoryItemWriter<Request> writer) {
        return new StepBuilder("stepRequestDeleteGropus", jobRepository)
                .<Request, Request> chunk(20, transactionManager)
                .reader(readerDeleteRegoleSicurezza(null)) //legge la riga dal db da lavorare
                .listener(new RequestStepExecutionListener())
                .listener(new RequestItemReadListener())
                .listener(new RequestItemProcessListener())
                .listener(new RequestItemWriteListener())
                .processor(new RequestItemProcessor())
                .writer(writer)
                // .taskExecutor(taskExecutor)
                .build();
    }



    @Bean
    public Step stepDeleteRegoleSicurezzaOim(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                              RepositoryItemWriter<Groups> writerGroupMembers
    ) {

        return new StepBuilder("stepDeleteOim", jobRepository)
                .<Groups, Groups> chunk(20, transactionManager)

                .reader(readerStepGruppi(null)) //legge le righe della groups dal db da lavorare

                .processor(processorDeleteRuoliOim(null)) //chiama oim e valorizza i campi della cancellazione
                .writer(new ItemWriter<Groups>() {
                    @Override
                    public void write(Chunk<? extends Groups> chunk) throws Exception {
                        //do nothing
                    }
                })
                //  .taskExecutor(taskExecutor)
                // .transactionAttribute(attribute)
                .build();
    }


    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<GroupMembers> readerStepGroupMember(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_MEMBER", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<GroupMembers>()
                .repository(groupMemberRepository)
                .methodName("getByRuolo")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

 */

}
