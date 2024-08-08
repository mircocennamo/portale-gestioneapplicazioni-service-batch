package it.interno.listener.applicMotivMember;

import it.interno.entity.ApplicMotivMembers;
import it.interno.entity.ApplicazioneMotivazione;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
public class ApplicMotivMemberSkipListener implements SkipListener<ApplicMotivMembers, ApplicMotivMembers> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("ApplicMotivMemberSkipListener On skip in read " + t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(ApplicMotivMembers item, Throwable t) {
        System.out.println("ApplicMotivMemberSkipListener On skip in write " + t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(ApplicMotivMembers item, Throwable t) {
        System.out.println("ApplicMotivMemberSkipListener On skip in process " + t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
