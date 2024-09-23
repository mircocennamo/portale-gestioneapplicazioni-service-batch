package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.GroupMembers;
import it.interno.repository.ApplicMotivMembersRepository;
import it.interno.repository.GroupMemberRepository;
import it.interno.repository.RegolaSicurezzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class GroupMemberCheckValiditaFindByAppIdReadListener implements ItemReadListener<GroupMembers> {

   // @Autowired
   // private OimClient oimClient;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private ApplicMotivMembersRepository applicMotivMembersRepository;

    @Autowired
    private RegolaSicurezzaRepository regolaSicurezzaRepository;



    OimClient oimClient;

    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    String appId;

    String nomeRuolo;


    @Value("${enable.oim}")
    private boolean enableOim;

    public GroupMemberCheckValiditaFindByAppIdReadListener(OimClient oimClient, String utenteCancellazione,
                                                           String ufficioCancellazione, Timestamp currentTimeStamp, String appId, String nomeRuolo){

        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
        this.appId=appId;
        this.oimClient=oimClient;
        this.nomeRuolo=nomeRuolo;
    }

    @Override
    public void beforeRead() {
        //do nothing
    }

    @Override
    public void afterRead(GroupMembers item) {
        List<GroupMembers> associazioni = groupMemberRepository.findByAppId(appId);
        if(associazioni.size()==1){
            GroupMembers groupMember =  associazioni.getFirst();
            if(regolaSicurezzaRepository.isRuoloApplicativoAssegnabile(groupMember.getNomeUtente(), appId, groupMember.getNomeRuolo()) == 0){
                groupMember.setUtenteCancellazione(utenteCancellazione);
                groupMember.setUfficioCancellazione(ufficioCancellazione);
                groupMember.setDataCancellazione(currentTimeStamp);
                groupMemberRepository.save(groupMember);
                if(enableOim){
                    oimClient.rimozioneRuolo(utenteCancellazione,groupMember.getNomeRuolo());
                }

            }
        }else{
            log.debug("esistono altri ruoli associati non cancello il master utente {} , idApp {} codiceRuolo {} " , utenteCancellazione, appId, nomeRuolo);
        }
    }

    @Override
    public void onReadError(Exception ex) {
        //do nothing
    }




}
