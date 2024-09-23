package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.ApplicMotivMembers;
import it.interno.entity.GroupMembers;
import it.interno.repository.ApplicMotivMembersRepository;
import it.interno.repository.GroupMemberRepository;
import it.interno.repository.RegolaSicurezzaRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class GroupMemberCheckValiditaUtenteDistintoItemProcessor implements ItemProcessor<String, GroupMembers> {

   // @Autowired
   // private OimClient oimClient;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private ApplicMotivMembersRepository applicMotivMembersRepository;

    @Autowired
    private RegolaSicurezzaRepository regolaSicurezzaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMemberCheckValiditaUtenteDistintoItemProcessor.class);

    OimClient oimClient;

    String utenteCancellazione;
    String ufficioCancellazione;

    Timestamp currentTimeStamp;

    String appId;

    String nomeRuolo;

    @Value("${enable.oim}")
    private boolean enableOim;




    public GroupMemberCheckValiditaUtenteDistintoItemProcessor(OimClient oimClient, String utenteCancellazione,
                                                               String ufficioCancellazione, Timestamp currentTimeStamp, String appId, String nomeRuolo){

        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
        this.appId=appId;
        this.oimClient=oimClient;
        this.nomeRuolo=nomeRuolo;
    }

    @Override
    public GroupMembers process(final String utenteApp) {
        //chiamo oim per cancellare l'utente dal gruppo

        List<GroupMembers> groupMembers = groupMemberRepository.getByUtenteAndAppNotInCodiceRuolo(utenteApp,appId,nomeRuolo);
        if(groupMembers.size()==1){
            log.debug("esiste solo il ruolo master lo cancello utente {} , idApp {} codiceRuolo {} " , utenteApp,appId,nomeRuolo);
            GroupMembers master =  groupMembers.getFirst();
            master.setUtenteCancellazione(utenteCancellazione);
            master.setUfficioCancellazione(ufficioCancellazione);
            master.setDataCancellazione(currentTimeStamp);
            if(enableOim){
                oimClient.rimozioneRuolo(master.getNomeUtente(),master.getNomeRuolo());
            }

            groupMemberRepository.save(master);

            List<ApplicMotivMembers>  applicMotivMembers = applicMotivMembersRepository.getByUtenteEApp(utenteApp, appId);
            applicMotivMembers.stream().forEach(applicMotivMember -> {
                applicMotivMember.setUtenteCancellazione(utenteCancellazione);
                applicMotivMember.setDataCancellazione(currentTimeStamp);
                applicMotivMember.setUfficioCancellazione(ufficioCancellazione);
            });
            applicMotivMembersRepository.saveAll(applicMotivMembers);
    }else{
            log.debug("esistono altri ruoli associati non cancello il master utente {} , idApp {} codiceRuolo {} " , utenteApp,appId,nomeRuolo);
    }

     return null;
    }




}
