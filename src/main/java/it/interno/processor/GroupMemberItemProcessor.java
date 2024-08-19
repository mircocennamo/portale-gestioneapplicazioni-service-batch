package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.GroupMembers;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;
import java.util.Arrays;

@Slf4j
public class GroupMemberItemProcessor implements ItemProcessor<GroupMembers, GroupMembers> {



    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMemberItemProcessor.class);

     OimClient oimClient;

    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    String appId;




    public GroupMemberItemProcessor(OimClient oimClient,String utenteCancellazione,
                                          String ufficioCancellazione,Timestamp currentTimeStamp,String appId){

        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
        this.appId=appId;
        this.oimClient=oimClient;
    }

    @Override
    public GroupMembers process(final GroupMembers groupMembers) {
        //chiamo oim per cancellare l'utente dal gruppo



        oimClient.rimozioneRuoloAUtenti(groupMembers.getNomeRuolo(), Arrays.asList( groupMembers.getNomeUtente()));
        //simulo errore oim
        //if(groupMembers.getNomeUtente().equals("bollo1")){
        //    throw new RuntimeException("Errore oim");
       // }

        groupMembers.setDataCancellazione(currentTimeStamp);
        groupMembers.setUtenteCancellazione(utenteCancellazione);
        groupMembers.setUfficioCancellazione(ufficioCancellazione);



        return groupMembers;
    }




}
