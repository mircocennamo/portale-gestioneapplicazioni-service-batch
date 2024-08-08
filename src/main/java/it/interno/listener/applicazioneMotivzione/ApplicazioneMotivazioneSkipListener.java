package it.interno.listener.applicazioneMotivzione;

import it.interno.entity.ApplicazioneMotivazione;
import it.interno.entity.Groups;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public class ApplicazioneMotivazioneSkipListener implements SkipListener<ApplicazioneMotivazione, ApplicazioneMotivazione> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("ApplicazioneMotivazioneSkipListener On skip in read " + t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(ApplicazioneMotivazione item, Throwable t) {
        System.out.println("ApplicazioneMotivazioneSkipListener On skip in write " + t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(ApplicazioneMotivazione item, Throwable t) {
        System.out.println("ApplicazioneMotivazioneSkipListener On skip in process " + t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
