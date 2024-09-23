package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.GroupMembers;
import it.interno.repository.RegolaSicurezzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

@Slf4j
public class GroupMemberCheckValiditaItemProcessor implements ItemProcessor<GroupMembers, GroupMembers> {



    @Autowired
    private RegolaSicurezzaRepository regolaSicurezzaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMemberCheckValiditaItemProcessor.class);

    OimClient oimClient;

    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    String appId;

    String nomeRuolo;

    @Value("${enable.oim}")
    private boolean enableOim;


    public GroupMemberCheckValiditaItemProcessor(OimClient oimClient, String utenteCancellazione,
                                                 String ufficioCancellazione, Timestamp currentTimeStamp, String appId,String nomeRuolo){

        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
        this.appId=appId;
        this.oimClient=oimClient;
        this.nomeRuolo=nomeRuolo;
    }

    @Override
    public GroupMembers process(final GroupMembers groupMembers) {
        //chiamo oim per cancellare l'utente dal gruppo

        if(regolaSicurezzaRepository.isRuoloApplicativoAssegnabile(groupMembers.getNomeUtente(), appId, nomeRuolo) == 0){
         //TODO test
        log.debug("simulo la chiamata alla store procedure isRuoloApplicativoAssegnabile" );

            // SI DEVE DISASSOCIARE IL RUOLO
            groupMembers.setUtenteCancellazione(utenteCancellazione);
            groupMembers.setUfficioCancellazione(ufficioCancellazione);
            groupMembers.setDataCancellazione(currentTimeStamp);
            if(enableOim){
                oimClient.rimozioneRuolo(groupMembers.getNomeUtente(),groupMembers.getNomeRuolo());
            }

        }




     // oimClient.rimozioneRuoloAUtenti(groupMembers.getNomeRuolo(), Arrays.asList( groupMembers.getNomeUtente()));
        //simulo errore oim
        //if(groupMembers.getNomeUtente().equals("bollo1")){
        //    throw new RuntimeException("Errore oim");
       // }

        return groupMembers;
    }




}
