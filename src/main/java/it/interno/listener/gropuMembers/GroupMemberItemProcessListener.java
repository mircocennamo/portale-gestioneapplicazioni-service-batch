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




    public GroupMemberItemProcessListener(){}

        @Override
        public void beforeProcess(GroupMembers item) {

            log.debug("GroupMemberItemProcessListener After read item: {} " , item);
            log.debug("GroupMemberItemProcessListener Before process request {} " , item);
        }

        @Override
        public void afterProcess(GroupMembers item, GroupMembers result) {

            log.debug("GroupMemberItemProcessListener After process request: {} " , item);
            log.debug("GroupMemberItemProcessListener After process groupmember: {} " , result);
         }

        @Override
        public void onProcessError(GroupMembers item, Exception e) {
            //skip
            log.debug("GroupMemberItemProcessListener On process error {} " , e);
        }
}
