package it.interno.processor;

import it.interno.entity.ApplicazioneMotivazione;
import it.interno.repository.ApplicazioneMotivazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;

@Slf4j
public class ApplicazioneMotivazioneItemProcessor implements ItemProcessor<ApplicazioneMotivazione, ApplicazioneMotivazione> {


    private ApplicazioneMotivazioneRepository applicazioneMotivazioneRepository;

    private String utenteCancellazione;

     private String ufficioCancellazione;
     private Timestamp currentTimeStamp;


    public ApplicazioneMotivazioneItemProcessor(ApplicazioneMotivazioneRepository applicazioneMotivazioneRepository,
                                               String utenteCancellazione, String ufficioCancellazione, Timestamp currentTimeStamp){
        this.applicazioneMotivazioneRepository=applicazioneMotivazioneRepository;
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

    @Override
    public ApplicazioneMotivazione process(final ApplicazioneMotivazione applicazioneMotivazione) {

        applicazioneMotivazione.setUtentecancellazione(utenteCancellazione);
        applicazioneMotivazione.setUfficioCancellazione(ufficioCancellazione);
        applicazioneMotivazione.setDataCancellazione(currentTimeStamp);
        return applicazioneMotivazione;
    }




}
