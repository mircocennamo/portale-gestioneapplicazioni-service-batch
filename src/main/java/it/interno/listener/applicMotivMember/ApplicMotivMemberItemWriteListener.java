package it.interno.listener.applicMotivMember;

import it.interno.entity.ApplicMotivMembers;
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
public class ApplicMotivMemberItemWriteListener implements ItemWriteListener<ApplicMotivMembers>{
    @Override
    public void beforeWrite(Chunk<? extends ApplicMotivMembers> items) {
        log.info("ApplicMotivMemberItemWriteListener Writing started Request list : " + items);
    }

    @Override
    public void afterWrite(Chunk<? extends ApplicMotivMembers> items) {
        log.info("ApplicMotivMemberItemWriteListener Writing completed Request list : " + items);
        ;
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends ApplicMotivMembers> items) {
        log.error("ApplicMotivMemberItemWriteListener Error in reading the Request records " + items);
        log.error("ApplicMotivMemberItemWriteListener Error in reading the Request records " + e);
    }
}
