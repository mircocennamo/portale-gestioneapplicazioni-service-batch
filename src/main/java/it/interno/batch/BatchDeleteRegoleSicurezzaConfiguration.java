package it.interno.batch;

import it.interno.client.OimClient;
import it.interno.entity.GroupMembers;
import it.interno.entity.RegolaSicurezza;
import it.interno.entity.Request;
import it.interno.enumeration.Operation;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.applicazione.RetryOimListener;
import it.interno.listener.applicazione.SkipOimGroupMemberListener;
import it.interno.listener.request.RequestItemProcessListener;
import it.interno.listener.request.RequestItemReadListener;
import it.interno.listener.request.RequestItemWriteListener;
import it.interno.listener.request.RequestStepExecutionListener;
import it.interno.processor.GroupMemberCheckValiditaFindByAppIdReadListener;
import it.interno.processor.GroupMemberCheckValiditaItemProcessor;
import it.interno.processor.GroupMemberCheckValiditaUtenteDistintoItemProcessor;
import it.interno.processor.RequestItemProcessor;
import it.interno.repository.GroupMemberRepository;
import it.interno.repository.RegolaSicurezzaRepository;
import it.interno.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
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
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.transaction.PlatformTransactionManager;

import java.net.http.HttpConnectTimeoutException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class BatchDeleteRegoleSicurezzaConfiguration {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RegolaSicurezzaRepository regolaSicurezzaRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    //@Autowired
    OimClient oimClient;

    public static final String JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH = "JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH";


    @Bean(name = JOB_DELETE_ALL_REGOLE_SICUREZZA_BATCH)
    public Job deleteAllRules(JobRepository jobRepository,
                                 JobCompletionNotificationListener JobCompletionNotificationListener,
                                 Step stepRequestDeleteAllRules,Step stepRegoleByNomeRuoloAndAppId,Step stepGroupMemberGetByRuolo,
                              Step stepGroupMemberUtentiDistintiByApp,Step stepGroupMemberfindByAppId) {
        return new JobBuilder("deleteAllRules", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(JobCompletionNotificationListener)
                .start(stepRequestDeleteAllRules)
                .next(stepRegoleByNomeRuoloAndAppId)
                .next(stepGroupMemberGetByRuolo)
                .next(stepGroupMemberUtentiDistintiByApp)
                .next(stepGroupMemberfindByAppId)
                .build();
    }





    /* *********************************************************** STEP SEZIONE GESTIONE REQUEST ******************************************************************** */


    @Bean
    public Step stepRequestDeleteAllRules(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                        RepositoryItemWriter<Request> writer) {
        return new StepBuilder("stepRequestDeleteAllRules", jobRepository)
                .<Request, Request> chunk(20, transactionManager)
                .reader(readerRequestDeleteAllRules(null)) //legge la riga dal db da lavorare
                .listener(new RequestStepExecutionListener())
                .listener(new RequestItemReadListener())
                .listener(new RequestItemProcessListener())
                .listener(new RequestItemWriteListener())
                .processor(new RequestItemProcessor())
                .writer(writer)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Request> readerRequestDeleteAllRules(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByStatusAndIdAppAndOperation")
                .arguments(Arrays.asList(applicationId, Operation.DELETE_ALL_REGOLE_SICUREZZA.getOperation()))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    /* ************************************************** FINE STEP SEZIONE GESTIONE REQUEST ******************************************************************** */


    /* **************************************************  STEP SEZIONE GESTIONE REGOLE  ******************************************************************** */

    @Bean
    public Step stepRegoleByNomeRuoloAndAppId(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                              RepositoryItemWriter<RegolaSicurezza> writerRegoleSicurezza
    ) {
        return new StepBuilder("stepRegoleByNomeRuoloAndAppId", jobRepository)
                .<RegolaSicurezza, RegolaSicurezza> chunk(20, transactionManager)
                .reader(readerRegoleByNomeRuoloAndAppId(null,null)) //legge le righe della groups dal db da lavorare
                //.listener(new GroupsStepExecutionListener())

                //.listener(groupItemProcessListener())
                //.listener(new GroupItemWriteListener()) //logga la scrittura sul db
                //.listener(new GroupsSkipListener())
                //.processor(processorGroup(null,null,null)) //chiama oim e valorizza i campi della cancellazione
                .writer(writerRegoleSicurezza)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<RegolaSicurezza> readerRegoleByNomeRuoloAndAppId(@Value(("#{jobParameters['nomeRuolo']}")) String nomeRuolo,
            @Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_NAME", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<RegolaSicurezza>()
                .repository(regolaSicurezzaRepository)
                .methodName("getRegoleByNomeRuoloAndAppId") //nomeRuolo idApplicazione
                .arguments(Arrays.asList(nomeRuolo,applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    /* **************************************************  STEP SEZIONE GROUP MEMBER (GET BY RUOLO) ******************************************************************** */


    @Bean
    public Step stepGroupMemberGetByRuolo(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                          RepositoryItemWriter<GroupMembers> writerGroupMembers
    ) {
        return new StepBuilder("stepGroupMemberGetByRuolo", jobRepository)
                .<GroupMembers, GroupMembers> chunk(20, transactionManager)
                .reader(readerGroupMemberGetByRuolo(null,null)) //legge le righe della groups dal db da lavorare
                //.listener(new GroupsStepExecutionListener())

                //.listener(groupItemProcessListener())
                //.listener(new GroupItemWriteListener()) //logga la scrittura sul db
                //.listener(new GroupsSkipListener())
                .processor(processorCheckValiditaGroupMember(null,null,
                        null,null,null)) //chiama oim e valorizza i campi della cancellazione (chiama la stored procedure)
                .writer(writerGroupMembers)
                .faultTolerant()
                .retryLimit(3)
                .retry(HttpConnectTimeoutException.class)
                .backOffPolicy(new ExponentialBackOffPolicy())
                .listener(new RetryOimListener())
                .skipLimit(10)
                .skip(Exception.class)
                .listener(new SkipOimGroupMemberListener())
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<GroupMembers> readerGroupMemberGetByRuolo(@Value(("#{jobParameters['nomeRuolo']}")) String nomeRuolo,
                                                                                 @Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_NAME", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<GroupMembers>()
                .repository(groupMemberRepository)
                .methodName("getByRuolo") //nomeRuolo idApplicazione
                .arguments(Arrays.asList(nomeRuolo,applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public GroupMemberCheckValiditaItemProcessor processorCheckValiditaGroupMember(
            @Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
            @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione,@Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp
            ,@Value(("#{jobParameters['applicationId']}")) String applicationId,@Value(("#{jobParameters['nomeRuolo']}")) String nomeRuolo

    ) {
        return new GroupMemberCheckValiditaItemProcessor(oimClient,utenteCancellazione,ufficioCancellazione,currentTimeStamp,applicationId,nomeRuolo);
    }


    /* **************************************************  STEP SEZIONE GROUP MEMBER (UTENTI DISTINTI BY APP) / (findByAppId) ******************************************************************** */
    @Bean
    public Step stepGroupMemberUtentiDistintiByApp(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                          RepositoryItemWriter<GroupMembers> writerGroupMembers
    ) {
        return new StepBuilder("stepGroupMemberUtentiDistintiByApp", jobRepository)
                .<String, GroupMembers> chunk(20, transactionManager)
                .reader(readerGroupMemberUtentiDistintiByApp(null)) //legge le righe della groups dal db da lavorare
                .processor(processorCheckValiditautenteDistintoGroupMember(null,null,
                        null,null,null)) //chiama oim e valorizza i campi della cancellazione
                .writer(chunk -> {
                    //do nothing
                })
                .faultTolerant()
                .retryLimit(3)
                .retry(HttpConnectTimeoutException.class)
                .backOffPolicy(new ExponentialBackOffPolicy())
                .listener(new RetryOimListener())
                .skipLimit(10)
                .skip(Exception.class)
                .listener(new SkipListener<>() {
                    @Override
                    public void onSkipInRead(Throwable t) {
                        log.error("Errore in lettura");
                    }

                    @Override
                    public void onSkipInWrite(GroupMembers item, Throwable t) {
                        log.error("Errore in scrittura");
                    }

                    @Override
                    public void onSkipInProcess(String item, Throwable t) {
                        log.error("Errore in processamento");
                    }
                })
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<String> readerGroupMemberUtentiDistintiByApp(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_MEMBER", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<String>()
                .repository(groupMemberRepository)
                .methodName("getUtentiDistintiByApp") // idApplicazione
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public GroupMemberCheckValiditaUtenteDistintoItemProcessor processorCheckValiditautenteDistintoGroupMember(
            @Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
            @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione,@Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp
            ,@Value(("#{jobParameters['applicationId']}")) String applicationId,@Value(("#{jobParameters['nomeRuolo']}")) String nomeRuolo

    ) {
        return new GroupMemberCheckValiditaUtenteDistintoItemProcessor(oimClient,utenteCancellazione,ufficioCancellazione,currentTimeStamp,applicationId,nomeRuolo);
    }


    /* **************************************************  STEP SEZIONE GROUP MEMBER  / stepGroupMemberfindByAppId (findByAppId) ******************************************************************** */


    @Bean
    public Step stepGroupMemberfindByAppId(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepGroupMemberfindByAppId", jobRepository)
                .<GroupMembers, GroupMembers> chunk(20, transactionManager)
                .reader(() -> null)
                .listener(groupMemberCheckValiditaFindByAppIdReadListener(null,null,
                        null,null,null))
                .writer(chunk -> {})
                .faultTolerant()
                .retryLimit(3)
                .retry(HttpConnectTimeoutException.class)
                .backOffPolicy(new ExponentialBackOffPolicy())
                .listener(new RetryOimListener())
                .skipLimit(10)
                .skip(Exception.class)
                .listener(new SkipOimGroupMemberListener())
                .build();
    }


    @Bean(destroyMethod = "")
    @StepScope
    public GroupMemberCheckValiditaFindByAppIdReadListener groupMemberCheckValiditaFindByAppIdReadListener(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                                                   @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione,
                                                                                   @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp,
                                                                                   @Value(("#{jobParameters['applicationId']}")) String applicationId,
                                                                                   @Value(("#{jobParameters['nomeRuolo']}")) String nomeRuolo) {
        return new GroupMemberCheckValiditaFindByAppIdReadListener(oimClient,utenteCancellazione,ufficioCancellazione,currentTimeStamp,applicationId,nomeRuolo);
    }
}
