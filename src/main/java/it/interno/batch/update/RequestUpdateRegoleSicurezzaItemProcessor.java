package it.interno.batch.update;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.interno.client.OimClient;
import it.interno.entity.RegolaSicurezza;
import it.interno.entity.Request;
import it.interno.repository.RegolaSicurezzaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class RequestUpdateRegoleSicurezzaItemProcessor implements ItemProcessor<Request, Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUpdateRegoleSicurezzaItemProcessor.class);

    @Autowired
    RegolaSicurezzaRepository regolaSicurezzaRepository;



    private long jobExecutionId;


    private String utente;
    private String ufficio;
    private Timestamp currentTimeStamp;
   @Autowired
   private ObjectMapper om;




    public RequestUpdateRegoleSicurezzaItemProcessor(String utente, String ufficio, Timestamp currentTimeStamp) {
        this.utente = utente;
        this.ufficio = ufficio;
        this.currentTimeStamp = currentTimeStamp;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecutionId = stepExecution.getJobExecutionId();
    }

    @Override
    public Request process(final Request request) throws JsonProcessingException {

        request.setJobId(jobExecutionId);
        List<RegolaSicurezza> results = Arrays.asList(om.readValue(request.getRegoleSicurezzaUpdate(), RegolaSicurezza[].class));

        results.stream().forEach(r-> {
            LOGGER.info("Processing request {} jobExecutionId {} RegoleSicurezza {} ", request, jobExecutionId, r);
            // Chiudo la regola vecchia
            List<RegolaSicurezza> daChiudere = regolaSicurezzaRepository.getRegoleByNomeRuoloAndAppIdAndIdRegola(r.getRuolo(), r.getAppId(), r.getNumeroRegola());
            daChiudere.forEach(el -> {
                el.setUtenteCancellazione(utente);
                el.setUfficioCancellazione(ufficio);
                el.setDataCancellazione(currentTimeStamp);
            });

            regolaSicurezzaRepository.deleteAll(daChiudere);

            // Salvo la regola nuova

            results.forEach(el -> {
                el.setUtenteInserimento(utente);
                el.setUfficioInserimento(ufficio);
                el.setDataInserimento(currentTimeStamp);
            });
            regolaSicurezzaRepository.saveAll(results);

        });
        LOGGER.info("set request {} jobExecutionId {} ", request, jobExecutionId);
        return request;
    }




}
