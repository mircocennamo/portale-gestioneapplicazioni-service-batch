package it.interno.processor;

import it.interno.entity.Request;
import it.interno.repository.GroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestItemProcessor implements ItemProcessor<Request, Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestItemProcessor.class);

    @Autowired
    GroupMemberRepository groupMemberRepository;

    @Override
    public Request process(final Request request)  {
        Request transformedRequest;
        String idApplicazione;
        String utenteCancellazione;
        String ufficioCancellazione;
        String operation;


        idApplicazione = request.getIdApplicazione().toUpperCase();
        utenteCancellazione = request.getUtenteCancellazione().toUpperCase();
        ufficioCancellazione = request.getUfficioCancellazione().toUpperCase();
        operation = request.getOperation().toUpperCase();

        //-------------------------------------------------------------------------------
        transformedRequest = new Request(request.getId(), idApplicazione, utenteCancellazione, ufficioCancellazione, operation, request.getStatus());
        LOGGER.info("Converting ( {} ) into ( {} )", request, transformedRequest);
/*try{
    Thread.sleep(20000);
}catch (InterruptedException e){
    System.out.println(e);
}

 */

         return transformedRequest;
    }




}
