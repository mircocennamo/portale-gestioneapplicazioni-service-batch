package it.interno.processor;

import it.interno.entity.Applicazione;
import it.interno.repository.ApplicazioneRepository;
import it.interno.utils.ConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;

@Slf4j
public class ApplicazioneItemProcessor implements ItemProcessor<Applicazione, Applicazione> {


    private ApplicazioneRepository applicazioneRepository;

    private String utenteCancellazione;

     private String ufficioCancellazione;
     private Timestamp currentTimeStamp;


    public ApplicazioneItemProcessor(ApplicazioneRepository applicazioneRepository,
                                     String utenteCancellazione, String ufficioCancellazione, Timestamp currentTimeStamp){
        this.applicazioneRepository=applicazioneRepository;
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

    @Override
    public Applicazione process(final Applicazione applicazione) {

        applicazione.setUtenteCancellazione(utenteCancellazione);
        applicazione.setUfficioCancellazione(ufficioCancellazione);
        applicazione.setDataCancellazione(currentTimeStamp);
        applicazione.setAppDataFin(ConversionUtils.timestampToLocalDate(currentTimeStamp));
        return applicazione;
    }


}
