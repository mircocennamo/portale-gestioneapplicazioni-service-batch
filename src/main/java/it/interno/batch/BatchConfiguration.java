package it.interno.batch;

import it.interno.client.OimClient;
import it.interno.entity.*;
import it.interno.listener.JobCompletionNotificationListener;
import it.interno.listener.RequestStepExecutionListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberItemProcessListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberItemWriteListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberSkipListener;
import it.interno.listener.applicMotivMember.ApplicMotivMemberStepExecutionListener;
import it.interno.listener.applicazione.ApplicazioneItemProcessListener;
import it.interno.listener.applicazione.ApplicazioneItemWriteListener;
import it.interno.listener.applicazione.ApplicazioneSkipListener;
import it.interno.listener.applicazione.ApplicazioneStepExecutionListener;
import it.interno.listener.applicazioneMotivzione.ApplicazioneMotivazioneItemProcessListener;
import it.interno.listener.gropuMembers.*;
import it.interno.listener.group.GroupItemProcessListener;
import it.interno.listener.group.GroupItemWriteListener;
import it.interno.listener.group.GroupsSkipListener;
import it.interno.listener.group.GroupsStepExecutionListener;
import it.interno.listener.regolesicurezza.RegoleSicurezzaSkipListener;
import it.interno.listener.regolesicurezza.RegoleSicurezzaStepExecutionListener;
import it.interno.listener.request.RequestItemProcessListener;
import it.interno.listener.request.RequestItemReadListener;
import it.interno.listener.request.RequestItemWriteListener;
import it.interno.processor.*;
import it.interno.repository.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Timestamp;
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

    @Autowired
    RegolaSicurezzaRepository regolaSicurezzaRepository;

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    GroupsAggregazioneRepository groupsAggregazioneRepository;

    @Autowired
    RuoloQualificaAssegnabilitaRepository ruoloQualificaAssegnabilitaRepository;

    //@Autowired
    OimClient oimClient;

    @Autowired
    ApplicazioneMotivazioneRepository applicazioneMotivazioneRepository;

    @Autowired
    ApplicMotivMembersRepository applicMotivMembersRepository;

    @Autowired
    ApplicazioneRepository applicazioneRepository;


    @Bean
    public VirtualThreadTaskExecutor taskExecutor() {
        return new VirtualThreadTaskExecutor("virtual-thread-executor");
    }


    @Bean
    public Job deleteApplication(JobRepository jobRepository, JobCompletionNotificationListener listener, Step stepRequest,
                                 Step stepGroupMember,Step stepRegoleSicurezza,Step stepGroups,
                                 Step stepApplicazioneMotivazione,Step stepApplicMotivMember,Step stepApplicazione) {
        return new JobBuilder("deleteApplicationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepRequest).next(stepGroupMember).next(stepRegoleSicurezza)
                .next(stepGroups).next(stepApplicazioneMotivazione)
                .next(stepApplicMotivMember).next(stepApplicazione).build();
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
                                           @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione,@Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new GroupMemberItemProcessListener(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }



    @Bean
    public RepositoryItemWriter<Request> writer() {
        return new RepositoryItemWriterBuilder<Request>().methodName("save").repository(requestRepository).build();

    }

    @Bean
    public Step stepRequest(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      RepositoryItemWriter<Request> writer, VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepRequest", jobRepository)
                .<Request, Request> chunk(1, transactionManager)
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
    public GroupMemberItemProcessor processorGroupMember() {
        return new GroupMemberItemProcessor();
    }


    @Bean
    public RepositoryItemWriter<GroupMembers> writerGroupMembers() {
        return new RepositoryItemWriterBuilder<GroupMembers>().methodName("save").repository(groupMemberRepository).build();

    }







    @Bean
    public Step stepGroupMember(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                RepositoryItemWriter<GroupMembers> writerGroupMembers,
                                VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepGroupMember", jobRepository)
                .<GroupMembers, GroupMembers> chunk(1, transactionManager)
                .reader(readerStepGroupMember(null)) //legge le righe della groupMemeber dal db da lavorare
                .listener(new GroupMemberStepExecutionListener())
                .listener(new GroupMemeberItemReadListener())
                .listener(groupMemberItemProcessListener(null,null,null)) //valorizza i campi della cancellazione
                .listener(new GroupMemberItemWriteListener()) //logga la scrittura sul db
                .listener(new GroupMemberSkipListener())
                .processor(processorGroupMember()) //chiama oim
                .writer(writerGroupMembers)
                .taskExecutor(taskExecutor)
                .build();
    }


//STEP 3 GESTIONE DI SICUREZZA

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<RegolaSicurezza> readerStepRegoleSicurezza(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_NAME", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<RegolaSicurezza>()
                .repository(regolaSicurezzaRepository)
                .methodName("getRegoleByAppId")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<RegolaSicurezza> writerRegoleSicurezza() {
        return new RepositoryItemWriterBuilder<RegolaSicurezza>().methodName("delete").repository(regolaSicurezzaRepository).build();

    }

    @Bean
    public Step stepRegoleSicurezza(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepRegoleSicurezza", jobRepository)
                .<RegolaSicurezza, RegolaSicurezza> chunk(1, transactionManager)
                .reader(readerStepRegoleSicurezza(null)) //legge le righe della groupMemeber dal db da lavorare
                 .listener(new RegoleSicurezzaStepExecutionListener())
                // .listener(new GroupMemeberItemReadListener(groupMemberRepository))
                // .listener(groupMemberItemProcessListener(null,null,null)) //valorizza i campi della cancellazione
                // .listener(new GroupMemberItemWriteListener()) //logga la scrittura sul db
                 .listener(new RegoleSicurezzaSkipListener())
                // .processor(processorGroup()) //chiama oim
                .writer(writerRegoleSicurezza())
                .taskExecutor(taskExecutor)
                .build();
    }


    //STEP 4 cancellazione gruppi / RuoloQualificaAssegnabilita / GROUPS_AGGREG

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Groups> readerStepGruppi(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_NAME", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Groups>()
                .repository(groupsRepository)
                .methodName("findAllByAppId")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Groups> writerGroup() {
        return new RepositoryItemWriterBuilder<Groups>().methodName("save").repository(groupsRepository).build();

    }

    @Bean
    public Step stepGroups(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                    VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepGroups", jobRepository)
                .<Groups, Groups> chunk(1, transactionManager)
                .reader(readerStepGruppi(null)) //legge le righe della groups dal db da lavorare
                .listener(new GroupsStepExecutionListener())
                // .listener(new GroupMemeberItemReadListener(groupMemberRepository))
                 .listener(groupItemProcessListener(null,null,null)) //valorizza i campi della cancellazione
                 .listener(new GroupItemWriteListener()) //logga la scrittura sul db
                .listener(new GroupsSkipListener())
                .processor(processorGroup(null,null,null)) //chiama oim
                .writer(writerGroup())
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public GroupItemProcessListener groupItemProcessListener(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                                   @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new GroupItemProcessListener(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }


    @Bean
    @StepScope
    public GroupsItemProcessor processorGroup(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                              @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new GroupsItemProcessor(groupsAggregazioneRepository,ruoloQualificaAssegnabilitaRepository,oimClient,utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }

    //STEP 5 cancellazione applicazioni Motivazione

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<ApplicazioneMotivazione> readerStepApplicazioneMotivazione(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("ID_TIPO_MOTIVAZIONE", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<ApplicazioneMotivazione>()
                .repository(applicazioneMotivazioneRepository)
                .methodName("findByIdApp")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<ApplicazioneMotivazione> writerApplicazioneMotivazione() {
        return new RepositoryItemWriterBuilder<ApplicazioneMotivazione>().methodName("save").repository(applicazioneMotivazioneRepository).build();

    }

    @Bean
    @StepScope
    public ApplicazioneMotivazioneItemProcessor processorApplicazioneMotivazione() {
        return new ApplicazioneMotivazioneItemProcessor();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public ApplicazioneMotivazioneItemProcessListener applicazioneMotivazioneItemProcessListener(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                             @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new ApplicazioneMotivazioneItemProcessListener(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }

    @Bean
    public Step stepApplicazioneMotivazione(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                           VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepApplicazioneMotivazione", jobRepository)
                .<ApplicazioneMotivazione, ApplicazioneMotivazione> chunk(1, transactionManager)
                .reader(readerStepApplicazioneMotivazione(null)) //legge le righe della tabella APPLICAZIONE MOTIVAZIONE dal db da lavorare
               // .listener(new ApplicazioneMotivazioneStepExecutionListener())
                // .listener(new GroupMemeberItemReadListener(groupMemberRepository))
                //.listener(new ApplicazioneMotivazioneItemWriteListener()) //logga la scrittura sul db
               // .listener(new ApplicazioneMotivazioneSkipListener())
                .listener(applicazioneMotivazioneItemProcessListener(null,null,null)) //valorizza i campi della cancellazione
                .processor(processorApplicazioneMotivazione()) //valorizza i campi della cancellazione logica
                .writer(writerApplicazioneMotivazione())
                .taskExecutor(taskExecutor)
                .build();
    }

    //STEP 6 cancellazione applic motiv member

    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<ApplicMotivMembers> readerStepApplicMotivMember(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("G_MEMBER", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<ApplicMotivMembers>()
                .repository(applicMotivMembersRepository)
                .methodName("getByApp")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<ApplicMotivMembers> writerApplicMotivMember() {
        return new RepositoryItemWriterBuilder<ApplicMotivMembers>().methodName("save").repository(applicMotivMembersRepository).build();

    }

    @Bean
    @StepScope
    public ApplicMotivMemberItemProcessor processorApplicMotivMember(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                                     @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione,
                                                                     @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new ApplicMotivMemberItemProcessor(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }


    @Bean(destroyMethod = "")
    @StepScope
    public ApplicMotivMemberItemProcessListener applicMotivMemberItemProcessListener(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                                                     @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new ApplicMotivMemberItemProcessListener(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }

    @Bean
    public Step stepApplicMotivMember(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                            VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepApplicMotivMember", jobRepository)
                .<ApplicMotivMembers, ApplicMotivMembers> chunk(1, transactionManager)
                .reader(readerStepApplicMotivMember(null)) //legge le righe della tabella SEC_APPLIC_MOTIV_MEMBERS dal db da lavorare
                .listener(new ApplicMotivMemberStepExecutionListener())
                // .listener(new GroupMemeberItemReadListener(groupMemberRepository))
                .listener(new ApplicMotivMemberItemWriteListener()) //logga la scrittura sul db
                .listener(new ApplicMotivMemberSkipListener())
                .listener(applicMotivMemberItemProcessListener(null,null,null)) //valorizza i campi della cancellazione logica
                .processor(processorApplicMotivMember(null,null,null))
                .writer(writerApplicMotivMember())
                .taskExecutor(taskExecutor)
                .build();
    }

    //STEP 7 cancellazione logica SEC_APPLICAZIONE
    @Bean(destroyMethod = "")
    @StepScope
    public RepositoryItemReader<Applicazione> readerStepApplicazione(@Value(("#{jobParameters['applicationId']}")) String applicationId) {
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("APP_ID", Sort.Direction.DESC);
        return new RepositoryItemReaderBuilder<Applicazione>()
                .repository(applicazioneRepository)
                .methodName("findById")
                .arguments(Arrays.asList(applicationId))
                .sorts(sortMap)
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Applicazione> writerApplicazione() {
        return new RepositoryItemWriterBuilder<Applicazione>().methodName("save").repository(applicazioneRepository).build();

    }

    @Bean
    @StepScope
    public ApplicazioneItemProcessor processorApplicazione(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                                     @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione,
                                                                     @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new ApplicazioneItemProcessor(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }

    @Bean
    public Step stepApplicazione(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                      VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("stepApplicazione", jobRepository)
                .<Applicazione, Applicazione> chunk(1, transactionManager)
                .reader(readerStepApplicazione(null)) //legge la riga della tabella SEC_APPLICAZIONE dal db da lavorare
                .listener(new ApplicazioneStepExecutionListener())
                .listener(new ApplicazioneItemWriteListener()) //logga la scrittura sul db
                .listener(new ApplicazioneSkipListener())
                .listener(applicazioneItemProcessListener(null,null,null)) //valorizza i campi della cancellazione logica
                .processor(processorApplicazione(null,null,null)) //valorizza i campi della cancellazione logica
                .writer(writerApplicazione())
                .taskExecutor(taskExecutor)
                .build();
    }


    @Bean(destroyMethod = "")
    @StepScope
    public ApplicazioneItemProcessListener applicazioneItemProcessListener(@Value(("#{jobParameters['utenteCancellazione']}")) String utenteCancellazione,
                                                                                @Value(("#{jobParameters['ufficioCancellazione']}")) String ufficioCancellazione, @Value(("#{jobParameters['currentTimeStamp']}")) Timestamp currentTimeStamp) {
        return new ApplicazioneItemProcessListener(utenteCancellazione,ufficioCancellazione,currentTimeStamp);
    }



    @Bean(name = "myJobLauncher")
    public JobLauncher simpleJobLauncher(JobRepository jobRepository){
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }
}
