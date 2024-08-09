package it.interno.listener.request;

import it.interno.entity.Request;
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
public class RequestItemWriteListener implements ItemWriteListener<Request>{
    @Override
    public void beforeWrite(Chunk<? extends Request> items) {
        log.info("RequestItemWriteListener Writing started Request list : {} " , items);
    }

    @Override
    public void afterWrite(Chunk<? extends Request> items) {
        log.info("RequestItemWriteListener Writing completed Request list : {} " , items);
        ;
    }

    @Override
    public void onWriteError(Exception e, Chunk<? extends Request> items) {
        log.error("RequestItemWriteListener Error in reading the Request records {} " ,  items);
        log.error("RequestItemWriteListener Error in reading the Request records {} " , e);
    }
}
