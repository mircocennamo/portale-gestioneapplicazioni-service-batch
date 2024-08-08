package it.interno.listener.request;

import it.interno.entity.Request;
import it.interno.enumeration.Status;
import it.interno.repository.RequestRepository;
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


    RequestRepository requestRepository;

    public RequestItemProcessListener(RequestRepository requestRepository){
        this.requestRepository=requestRepository;
    }

        @Override
        public void beforeProcess(Request item) {
            item.setStatus(Status.RUNNING.getStatus());
           // requestRepository.save(item);
            log.debug("After read item: {} " , item);
            System.out.println("RequestItemProcessListener Before process");
        }

        @Override
        public void afterProcess(Request item, Request result) {
           // result.setStatus(Status.COMPLETED.getStatus());
           // requestRepository.save(item);
            System.out.println("After process item: " + item);
        }

        @Override
        public void onProcessError(Request item, Exception e) {
            item.setStatus(Status.FAILED.getStatus());
            //requestRepository.save(item);
            System.out.println("On process error " + e);
        }
}
