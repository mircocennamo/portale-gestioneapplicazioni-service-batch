package it.interno.listener.applicazioneMotivazione;

import it.interno.entity.ApplicazioneMotivazione;
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
public class ApplicazioneMotivazioneItemWriteListener implements ItemWriteListener<ApplicazioneMotivazione>{
    @Override
    public void beforeWrite(Chunk<? extends ApplicazioneMotivazione> items) {
        log.info("ApplicazioneMotivazioneItemWriteListener Writing started Request list : " + items);
    }

    @Override
    public void afterWrite(Chunk<? extends ApplicazioneMotivazione> items) {
        log.info("ApplicazioneMotivazioneItemWriteListener Writing completed Request list : " + items);
        ;
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends ApplicazioneMotivazione> items) {
        log.error("ApplicazioneMotivazioneItemWriteListener Error in reading the Request records " + items);
        log.error("ApplicazioneMotivazioneItemWriteListener Error in reading the Request records " + e);
    }
}
