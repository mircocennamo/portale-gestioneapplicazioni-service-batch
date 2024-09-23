package it.interno.batch.update;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.interno.client.OimClient;
import it.interno.domain.RuoloOimDto;
import it.interno.entity.Groups;
import it.interno.entity.GroupsKey;
import it.interno.entity.Request;
import it.interno.repository.GroupsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RequestUpdateItemProcessor implements ItemProcessor<Request, Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUpdateItemProcessor.class);

    @Autowired
    GroupsRepository groupsRepository;

    OimClient oimClient;

    private long jobExecutionId;


    private String utente;
    private String ufficio;
    private Timestamp currentTimeStamp;
    private ObjectMapper om = new ObjectMapper();

    @Value("${enable.oim}")
    private boolean enableOim;


    public RequestUpdateItemProcessor(OimClient oimClient,String utente, String ufficio, Timestamp currentTimeStamp) {
        this.utente = utente;
        this.ufficio = ufficio;
        this.currentTimeStamp = currentTimeStamp;
        this.oimClient=oimClient;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecutionId = stepExecution.getJobExecutionId();
    }

    @Override
    public Request process(final Request request) throws JsonProcessingException {

        request.setJobId(jobExecutionId);
        List<GroupsKey> results = Arrays.asList(om.readValue(request.getGroupUpdate(), GroupsKey[].class));

        results.stream().forEach(r-> {
            LOGGER.info("Processing request {} jobExecutionId {} groupUpdate {} ", request, jobExecutionId, r);
            Groups group =  groupsRepository.getById(r);
            if(enableOim){
                if(group.getDataCancellazione()==null){
                    oimClient.modificaRuoli( Collections.singletonList(new RuoloOimDto(
                            group.getNome(),
                            group.getDescrizione()
                    )));
                }
            }


            group.setUtenteAggiornamento(utente);
            group.setUfficioAggiornamento(ufficio);
            group.setDataAggiornamento(currentTimeStamp);
            groupsRepository.save(group);

        });







        LOGGER.info("set request {} jobExecutionId {} ", request, jobExecutionId);




         return request;
    }




}
