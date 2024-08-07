package it.interno.listener.group;

import it.interno.entity.Groups;
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
public class GroupItemWriteListener implements ItemWriteListener<Groups>{
    @Override
    public void beforeWrite(Chunk<? extends Groups> items) {
        log.info("GroupItemWriteListener Writing started Request list : " + items);
    }

    @Override
    public void afterWrite(Chunk<? extends Groups> items) {
        log.info("GroupItemWriteListener Writing completed Request list : " + items);
        ;
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends Groups> items) {
        log.error("GroupItemWriteListener Error in reading the Request records " + items);
        log.error("GroupItemWriteListener Error in reading the Request records " + e);
    }
}
