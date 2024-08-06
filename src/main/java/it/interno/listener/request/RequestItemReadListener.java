package it.interno.listener.request;

import it.interno.entity.Request;
import it.interno.enumeration.Status;
import it.interno.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class RequestItemReadListener implements ItemReadListener<Request> {


    RequestRepository requestRepository;

    public RequestItemReadListener(RequestRepository requestRepository){
        this.requestRepository=requestRepository;
    }

    @Override
    public void beforeRead() {
        log.debug("RequestItemReadListener Before read");
    }

    @Override
    public void afterRead(Request item) {
        item.setStatus(Status.STARTED.getStatus());
        requestRepository.save(item);
        log.debug("After read item: {} " , item);
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("On read error {} " ,ex);
    }
}
