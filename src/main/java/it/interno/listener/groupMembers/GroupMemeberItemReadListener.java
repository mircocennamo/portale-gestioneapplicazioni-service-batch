package it.interno.listener.groupMembers;

import it.interno.entity.GroupMembers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Slf4j
@Component
public class GroupMemeberItemReadListener implements ItemReadListener<GroupMembers> {




    public GroupMemeberItemReadListener(){

    }

    @Override
    public void beforeRead() {
        System.out.println("GroupMemeberItemReadListener Before read");
    }

    @Override
    public void afterRead(GroupMembers item) {

        System.out.println("After read item:  " + item);
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("On read error  " + ex);
    }
}
