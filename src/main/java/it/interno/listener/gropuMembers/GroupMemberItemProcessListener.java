package it.interno.listener.gropuMembers;

import it.interno.entity.GroupMembers;
import it.interno.repository.GroupMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class GroupMemberItemProcessListener implements ItemProcessListener<GroupMembers,GroupMembers> {


    GroupMemberRepository groupMemberRepository;
    String utenteCancellazione;
    String ufficioCancellazione;

    public GroupMemberItemProcessListener(GroupMemberRepository groupMemberRepository,String utenteCancellazione,String ufficioCancellazione){
        this.groupMemberRepository=groupMemberRepository;
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
    }

        @Override
        public void beforeProcess(GroupMembers item) {

            log.debug("After read item: {} " , item);
            System.out.println("RequestItemProcessListener Before process request " + item);
        }

        @Override
        public void afterProcess(GroupMembers item, GroupMembers result) {
            result.setDataCancellazione(new Timestamp(Instant.now().toEpochMilli()));
            result.setUtenteCancellazione(utenteCancellazione);
            result.setUfficioCancellazione(ufficioCancellazione);
            System.out.println("After process request: " + item);
            System.out.println("After process groupmember: " + result);
            groupMemberRepository.save(result);

        }

        @Override
        public void onProcessError(GroupMembers item, Exception e) {
            //skip
            System.out.println("On process error " + e);
        }
}
