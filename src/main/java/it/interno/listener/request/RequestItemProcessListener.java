package it.interno.listener.request;

import it.interno.entity.Request;
import it.interno.enumeration.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class RequestItemProcessListener implements ItemProcessListener<Request,Request> {




    public RequestItemProcessListener(){}

        @Override
        public void beforeProcess(Request item) {
            item.setStatus(Status.ASSIGNED.getStatus());
           // requestRepository.save(item);
            log.debug("After read item: {} " , item);
            log.debug("RequestItemProcessListener Before process");
        }

        @Override
        public void afterProcess(Request item, Request result) {
           // result.setStatus(Status.COMPLETED.getStatus());
           // requestRepository.save(item);
            log.debug("After process item: {} " , item);
        }

        @Override
        public void onProcessError(Request item, Exception e) {
            log.debug("On process error {} " , e);
        }
}
