package it.interno.listener.group;

import it.interno.entity.GroupMembers;
import it.interno.entity.Groups;
import it.interno.repository.GroupMemberRepository;
import it.interno.repository.GroupsRepository;
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
public class GroupItemProcessListener implements ItemProcessListener<Groups,Groups> {


    GroupsRepository groupsRepository;
    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    public GroupItemProcessListener(GroupsRepository groupsRepository, String utenteCancellazione,
                                    String ufficioCancellazione, Timestamp currentTimeStamp){
        this.groupsRepository=groupsRepository;
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

        @Override
        public void beforeProcess(Groups item) {

            log.debug("After read item: {} " , item);
            System.out.println("GroupItemProcessListener Before process request " + item);
        }

        @Override
        public void afterProcess(Groups item, Groups result) {
            result.setDataCancellazione(currentTimeStamp);
            result.setUtenteCancellazione(utenteCancellazione);
            result.setUfficioCancellazione(ufficioCancellazione);
            System.out.println("After process request: " + item);
            System.out.println("After process groupmember: " + result);
            groupsRepository.save(result);

        }

        @Override
        public void onProcessError(Groups item, Exception e) {
            //skip
            System.out.println("On process error " + e);
        }
}
