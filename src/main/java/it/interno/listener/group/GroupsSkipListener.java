package it.interno.listener.group;

import it.interno.entity.Groups;
import it.interno.entity.RegolaSicurezza;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public class GroupsSkipListener implements SkipListener<Groups, Groups> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("GroupsSkipListener On skip in read " + t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(Groups item, Throwable t) {
        System.out.println("GroupsSkipListener On skip in write " + t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(Groups item, Throwable t) {
        System.out.println("GroupsSkipListener On skip in process " + t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
