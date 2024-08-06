package it.interno.listener.gropuMembers;

import it.interno.entity.GroupMembers;
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
public class GroupMemberItemWriteListener implements ItemWriteListener<GroupMembers>{
    @Override
    public void beforeWrite(Chunk<? extends GroupMembers> items) {
        log.info("Writing started Request list : " + items);
    }

    @Override
    public void afterWrite(Chunk<? extends GroupMembers> items) {
        log.info("Writing completed Request list : " + items);
        ;
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends GroupMembers> items) {
        log.error("Error in reading the Request records " + items);
        log.error("Error in reading the Request records " + e);
    }
}
