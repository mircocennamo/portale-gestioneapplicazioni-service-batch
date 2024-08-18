package it.interno.listener.applicazione;


import it.interno.entity.Groups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 18/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
public class SkipOimListener implements SkipListener<Groups, Groups> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        log.debug("ApplicazioneSkipListener On skip in read  {} " , t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(Groups item, Throwable t) {
        log.debug("ApplicazioneSkipListener On skip in write  {} " , t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(Groups item, Throwable t) {
        log.debug("ApplicazioneSkipListener On skip in process {} " , t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}

