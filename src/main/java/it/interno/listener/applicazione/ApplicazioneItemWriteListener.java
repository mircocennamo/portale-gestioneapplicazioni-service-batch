package it.interno.listener.applicazione;

import it.interno.entity.Applicazione;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class ApplicazioneItemWriteListener implements ItemWriteListener<Applicazione>{
    @Override
    public void beforeWrite(Chunk<? extends Applicazione> items) {
        log.info("ApplicazioneItemWriteListener Writing started Request list : " + items);
    }

    @Override
    public void afterWrite(Chunk<? extends Applicazione> items) {
        log.info("ApplicazioneItemWriteListener Writing completed Request list : " + items);
        ;
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends Applicazione> items) {
        log.error("ApplicazioneItemWriteListener Error in reading the Request records " + items);
        log.error("ApplicazioneItemWriteListener Error in reading the Request records " + e);
    }
}
