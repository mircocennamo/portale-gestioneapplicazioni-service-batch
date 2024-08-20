package it.interno.batch;

import it.interno.client.OimClient;
import it.interno.entity.*;
import it.interno.enumeration.Operation;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberItemProcessListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberItemWriteListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberStepExecutionListener;
import it.interno.listener.applicazione.RetryOimListener;
import it.interno.listener.applicazione.SkipOimGroupMemberListener;
import it.interno.listener.applicazioneMotivazione.ApplicazioneMotivazioneItemProcessListener;
import it.interno.listener.request.RequestItemProcessListener;
import it.interno.listener.request.RequestItemReadListener;
import it.interno.listener.request.RequestItemWriteListener;
import it.interno.listener.request.RequestStepExecutionListener;
import it.interno.processor.*;
import it.interno.repository.*;
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
public class BatchDeleteMotivazioniConfiguration {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ApplicMotivMembersRepository secApplicMotivMemberRepository;

    @Autowired
    ApplicazioneMotivazioneRepository applicazioneMotivazioneRepository;

    //@Autowired
    OimClient oimClient;

    public static final String JOB_DELETE_ALL_MOTIVAZIONI_BATCH = "JOB_DELETE_ALL_MOTIVAZIONI_BATCH";


    @Bean(name = JOB_DELETE_ALL_MOTIVAZIONI_BATCH)
    public Job deleteAllMotivazioni(JobRepository jobRepository,
                                 JobCompletionNotificationListener JobCompletionNotificationListener,
                                 Step stepRequestDeleteAllMotivazioni,Step stepSecApplicMotivMemberDelete,Step stepSecApplMotivazioneDelete) {
        return new JobBuilder("deleteAllMotivazioni", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(JobCompletionNotificationListener)
                .start(stepRequestDeleteAllMotivazioni)
                .next(stepSecApplicMotivMemberDelete)
                .next(stepSecApplMotivazioneDelete)
                .build();
    }





    /* *********************************************************** STEP SEZIONE GESTIONE REQUEST ******************************************************************** */


    @Bean
    public Step stepRequestDeleteAllMotivazioni(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                        RepositoryItemWriter<Request> writer) {
        return new StepBuilder("stepRequestDeleteAllMotivazioni", jobRepository)
                .<Request, Request> chunk(20, transactionManager)
                .reader(readerRequestDeleteAllMotivazioni(null)) //legge la riga dal db da lavorare
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
    public RepositoryItemReader<Request> readerRequestDeleteAllMotivazioni(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Request>()
                .repository(requestRepository)
                .methodName("findRequestByStatusAndIdAppAndOperation")
                .arguments(Arrays.asList(applicationId, Operation.DELETE_ALL_MOTIVAZIONI.getOperation()))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    /* ************************************************** FINE STEP SEZIONE GESTIONE REQUEST ******************************************************************** */



    /* **************************************************  STEP SEZIONE GESTIONE SEC_APPLIC_MOTIV_MEMBER ******************************************************************** */

    @Bean
    public Step stepSecApplicMotivMemberDelete(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                               ApplicMotivMemberItemProcessor processorApplicMotivMember,
                                               ApplicMotivMemberItemProcessListener applicMotivMemberItemProcessListener,
                                               RepositoryItemWriter<ApplicMotivMembers> writerApplicMotivMember) {
        return new StepBuilder("stepSecApplicMotivMemberDelete", jobRepository)
                .<ApplicMotivMembers, ApplicMotivMembers> chunk(20, transactionManager)
                .reader(readerDeleteSecApplicMotivMember(null,null)) //legge la riga dal db da lavorare
                .listener(new ApplicMotivMemberStepExecutionListener())
                .listener(new ApplicMotivMemberItemWriteListener())
                .listener(applicMotivMemberItemProcessListener)
                .processor(processorApplicMotivMember)
                .writer(writerApplicMotivMember)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<ApplicMotivMembers> readerDeleteSecApplicMotivMember(@Value(("#{jobParameters['applicationId']}")) String applicationId,
                                                                                     @Value(("#{jobParameters['tipoMotivazioneId']}")) String tipoMotivazioneId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("APP_ID", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<ApplicMotivMembers>()
                .repository(secApplicMotivMemberRepository)
                .methodName("getByIdAppAndIdTipoMotivazione")
                .arguments(Arrays.asList(applicationId, tipoMotivazioneId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    /* ************************************************** FINE STEP SEZIONE GESTIONE SEC_APPLIC_MOTIV_MEMBER ******************************************************************** */


    /* **************************************************  STEP SEZIONE GESTIONE SEC_APP_MOTIVAZIONE ******************************************************************** */

    @Bean
    public Step stepSecApplMotivazioneDelete(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                             ApplicazioneMotivazioneItemProcessor processorApplicazioneMotivazione,
                                             RepositoryItemWriter<ApplicazioneMotivazione> writerApplicazioneMotivazione, ApplicazioneMotivazioneItemProcessListener applicazioneMotivazioneItemProcessListener) {
        return new StepBuilder("stepSecApplMotivazioneDelete", jobRepository)
                .<ApplicazioneMotivazione, ApplicazioneMotivazione> chunk(20, transactionManager)
                .reader(readerStepApplicazioneMotivazioneByKey(null,null)) //legge la riga dal db da lavorare
                .listener(applicazioneMotivazioneItemProcessListener) //valorizza i campi della cancellazione
                .processor(processorApplicazioneMotivazione) //valorizza i campi della cancellazione logica
                .writer(writerApplicazioneMotivazione) //scrive la riga aggiornata
                .build();
    }


    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<ApplicazioneMotivazione> readerStepApplicazioneMotivazioneByKey(@Value(("#{jobParameters['applicationId']}")) String applicationId,
                                                                                                @Value(("#{jobParameters['tipoMotivazioneId']}")) String tipoMotivazioneId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("ID_TIPO_MOTIVAZIONE", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<ApplicazioneMotivazione>()
                .repository(applicazioneMotivazioneRepository)
                .methodName("findById")
                .arguments(Arrays.asList(applicationId, tipoMotivazioneId))
                .sorts(sortMap)
                .saveState(false)
                .build();

    }
}
