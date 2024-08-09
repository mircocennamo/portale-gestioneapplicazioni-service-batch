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





    public GroupItemProcessListener(){}

        @Override
        public void beforeProcess(Groups item) {

            log.debug("GroupItemProcessListener After read item: {} " , item);
            log.debug("GroupItemProcessListener Before process request " + item);
        }

        @Override
        public void afterProcess(Groups item, Groups result) {

            log.debug("GroupItemProcessListener After process request: " + item);
            log.debug("GroupItemProcessListener After process groupmember: " + result);


        }

        @Override
        public void onProcessError(Groups item, Exception e) {
            //skip
            log.debug("GroupItemProcessListener On process error " + e);
        }
}
