package it.interno.listener.applicazione;

import it.interno.entity.Applicazione;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public class ApplicazioneSkipListener implements SkipListener<Applicazione, Applicazione> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("ApplicazioneSkipListener On skip in read " + t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(Applicazione item, Throwable t) {
        System.out.println("ApplicazioneSkipListener On skip in write " + t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(Applicazione item, Throwable t) {
        System.out.println("ApplicazioneSkipListener On skip in process " + t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
