package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.GroupMembers;
import it.interno.repository.GroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class GroupMemberItemProcessor implements ItemProcessor<GroupMembers, GroupMembers> {

   // @Autowired
   // private OimClient oimClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMemberItemProcessor.class);

    @Autowired
    GroupMemberRepository groupMemberRepository;

    @Override
    public GroupMembers process(final GroupMembers groupMembers) {
        //chiamo oim per cancellare l'utente dal gruppo
        GroupMembers g = new GroupMembers(groupMembers.getNomeUtente(),groupMembers.getNomeRuolo());
        g.setAppId(groupMembers.getAppId());
       // oimClient.rimozioneRuoloAUtenti(groupMembers.getNomeRuolo(), Arrays.asList( groupMembers.getNomeUtente()));

        return g;
    }




}
