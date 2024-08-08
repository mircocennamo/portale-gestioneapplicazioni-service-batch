package it.interno.listener.applicMotivMember;

import it.interno.entity.ApplicMotivMembers;
import it.interno.repository.ApplicMotivMembersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class ApplicMotivMemberItemProcessListener implements ItemProcessListener<ApplicMotivMembers,ApplicMotivMembers> {



    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    public ApplicMotivMemberItemProcessListener(String utenteCancellazione,
                                                String ufficioCancellazione, Timestamp currentTimeStamp){

        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

        @Override
        public void beforeProcess(ApplicMotivMembers item) {

            log.debug("After read item: {} " , item);
            System.out.println("GroupItemProcessListener Before process request " + item);
        }

        @Override
        public void afterProcess(ApplicMotivMembers item, ApplicMotivMembers result) {
            result.setDataCancellazione(currentTimeStamp);
            result.setUtenteCancellazione(utenteCancellazione);
            result.setUfficioCancellazione(ufficioCancellazione);
            System.out.println("After process request: " + item);
            System.out.println("After process groupmember: " + result);


        }

        @Override
        public void onProcessError(ApplicMotivMembers item, Exception e) {
            //skip
            System.out.println("On process error " + e);
        }
}
