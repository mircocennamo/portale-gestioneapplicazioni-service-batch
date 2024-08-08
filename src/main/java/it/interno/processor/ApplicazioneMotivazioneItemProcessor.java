package it.interno.processor;

import it.interno.entity.ApplicazioneMotivazione;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;

@Slf4j
public class ApplicazioneMotivazioneItemProcessor implements ItemProcessor<ApplicazioneMotivazione, ApplicazioneMotivazione> {







    public ApplicazioneMotivazioneItemProcessor(){


    }

    @Override
    public ApplicazioneMotivazione process(final ApplicazioneMotivazione applicazioneMotivazione) {


        return applicazioneMotivazione;
    }




}
