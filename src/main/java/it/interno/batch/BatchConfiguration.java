package it.interno.batch;

import it.interno.entity.GroupMembers;
import it.interno.entity.Request;
import it.interno.listener.GroupMemberStepExecutionListener;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.RequestStepExecutionListener;
import it.interno.listener.gropuMembers.GroupMemberItemProcessListener;
import it.interno.listener.gropuMembers.GroupMemberItemWriteListener;
import it.interno.listener.gropuMembers.GroupMemberSkipListener;
import it.interno.listener.gropuMembers.GroupMemeberItemReadListener;
import it.interno.listener.request.RequestItemProcessListener;
import it.interno.listener.request.RequestItemReadListener;
import it.interno.listener.request.RequestItemWriteListener;
import it.interno.processor.GroupMemberItemProcessor;
import it.interno.processor.RequestItemProcessor;
import it.interno.repository.GroupMemberRepository;
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
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories("it.interno.repository")
@EntityScan("it.interno.entity")
public class BatchConfiguration {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    GroupMemberRepository groupMemberRepository;


    @Bean
    public VirtualThreadTaskExecutor taskExecutor() {
        return new VirtualThreadTaskExecutor("virtual-thread-executor");
    }


    @Bean
    public Job deleteApplication(JobRepository jobRepository, JobCompletionNotificationListener listener, Step stepRequest, Step stepGroupMember) {
        return new JobBuilder("deleteApplicationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepRequest).next(stepGroupMember).build();
    }


   /* step1 --> recupero request da eseguire dal DB in base all'id dell'applicazione
   dopo la lettura lo stato passa a STARTED -> PRIMA DEL PROCESSAMENTO RUNNING
    */


    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Request> reader(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
            return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByStatusAndIdApp")
                .arguments(Arrays.asList("1",applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public RequestItemProcessor processor() {
        return new RequestItemProcessor();
    }


    @Bean(destroyMethod = "")
    @StepScope
    public  GroupMemberItemProcessListener groupMemberItemProcessListener(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                           @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione) {
        return new GroupMemberItemProcessListener(groupMemberRepository,utenteCancellazione,ufficioCancellazione);
    }



    @Bean
    public RepositoryItemWriter<Request> writer() {
        return new RepositoryItemWriterBuilder<Request>().methodName("save").repository(requestRepository).build();

    }

    @Bean
    public Step stepRequest(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      RepositoryItemWriter<Request> writer, VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepRequest", jobRepository)
                .<Request, Request> chunk(10, transactionManager)
                .reader(reader(null)) //legge la riga dal db da lavorare
                .listener(new RequestStepExecutionListener())
                .listener(new RequestItemReadListener(requestRepository))
                .listener(new RequestItemProcessListener(requestRepository))
                .listener(new RequestItemWriteListener())
                .processor(processor())
                .writer(writer)
                .taskExecutor(taskExecutor)
                .build();
    }



    /* step2 --> recupero le groupmemembers  dal DB in base all'id dell'applicazione,chiamo oim e aggiorno la tabella GROUPMEMBERS con
    dataCancellazione / utenteCancellazione /ufficioCancellazione
     */

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<GroupMembers> readerStepGroupMember(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_MEMBER", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<GroupMembers>()
                .repository(groupMemberRepository)
                .methodName("findByAppId")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public GroupMemberItemProcessor processorGroup() {
        return new GroupMemberItemProcessor();
    }


    @Bean
    public RepositoryItemWriter<GroupMembers> writerGroup() {
        return new RepositoryItemWriterBuilder<GroupMembers>().methodName("save").repository(groupMemberRepository).build();

    }







    @Bean
    public Step stepGroupMember(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                RepositoryItemWriter<GroupMembers> writerGroup,
                                VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepGroupMember", jobRepository)
                .<GroupMembers, GroupMembers> chunk(10, transactionManager)
                .reader(readerStepGroupMember(null)) //legge le righe della groupMemeber dal db da lavorare
                .listener(new GroupMemberStepExecutionListener())
                .listener(new GroupMemeberItemReadListener(groupMemberRepository))
                .listener(groupMemberItemProcessListener(null,null)) //valorizza i campi della cancellazione
                .listener(new GroupMemberItemWriteListener()) //logga la scrittura sul db
                .listener(new GroupMemberSkipListener())
                .processor(processorGroup()) //chiama oim
                .writer(writerGroup)
                .taskExecutor(taskExecutor)
                .build();
    }




}
