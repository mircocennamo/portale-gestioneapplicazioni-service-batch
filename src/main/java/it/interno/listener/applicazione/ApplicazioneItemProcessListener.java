package it.interno.listener.applicazione;

import it.interno.entity.Applicazione;
import it.interno.repository.ApplicazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class ApplicazioneItemProcessListener implements ItemProcessListener<Applicazione,Applicazione> {



    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    public ApplicazioneItemProcessListener(String utenteCancellazione, String ufficioCancellazione, Timestamp currentTimeStamp){

        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

        @Override
        public void beforeProcess(Applicazione item) {

            log.debug("After read item: {} " , item);
            System.out.println("ApplicazioneItemProcessListener Before process request " + item);
        }

        @Override
        public void afterProcess(Applicazione item, Applicazione result) {
            result.setDataCancellazione(currentTimeStamp);
            result.setUtenteCancellazione(utenteCancellazione);
            result.setUfficioCancellazione(ufficioCancellazione);
            System.out.println("After process request: " + item);
            System.out.println("After process groupmember: " + result);


        }

        @Override
        public void onProcessError(Applicazione item, Exception e) {
            //skip
            System.out.println("On process error " + e);
        }
}
