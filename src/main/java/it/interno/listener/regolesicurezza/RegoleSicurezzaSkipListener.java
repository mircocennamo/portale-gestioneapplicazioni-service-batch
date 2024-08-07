package it.interno.listener.regolesicurezza;

import it.interno.entity.GroupMembers;
import it.interno.entity.RegolaSicurezza;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public class RegoleSicurezzaSkipListener implements SkipListener<RegolaSicurezza, RegolaSicurezza> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("On skip in read " + t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(RegolaSicurezza item, Throwable t) {
        System.out.println("On skip in write " + t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(RegolaSicurezza item, Throwable t) {
        System.out.println("On skip in process " + t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
