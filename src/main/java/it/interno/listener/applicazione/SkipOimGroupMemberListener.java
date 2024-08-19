package it.interno.listener.applicazione;


import it.interno.entity.GroupMembers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

/**
 * @author mirco.cennamo on 18/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
public class SkipOimGroupMemberListener implements SkipListener<GroupMembers, GroupMembers> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        log.debug("SkipOimGroupMemberListener On skip in read  {} " , t);
        skipCount++;
    }

    @Override
    public void onSkipInWrite(GroupMembers item, Throwable t) {
        log.debug("SkipOimGroupMemberListener On skip in write  {} " , t);
        skipCount++;
    }

    @Override
    public void onSkipInProcess(GroupMembers item, Throwable t) {
        log.debug("SkipOimGroupMemberListener On skip in process {} " , t);
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}

