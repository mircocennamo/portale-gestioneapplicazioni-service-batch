package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.Groups;
import it.interno.entity.GroupsAggregazione;
import it.interno.repository.GroupsAggregazioneRepository;
import it.interno.repository.GroupsRepository;
import it.interno.repository.RuoloQualificaAssegnabilitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class GroupsItemProcessor implements ItemProcessor<Groups, Groups> {

   // @Autowired
   // private OimClient oimClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupsItemProcessor.class);


    GroupsRepository groupsRepository;


    private GroupsAggregazioneRepository groupsAggregazioneRepository;


    private RuoloQualificaAssegnabilitaRepository ruoloQualificaAssegnabilitaRepository;


     private OimClient oimClient;

     private String utenteCancellazione;

     private String ufficioCancellazione;
     private Timestamp currentTimeStamp;


    public GroupsItemProcessor(GroupsRepository groupsRepository,
                               GroupsAggregazioneRepository groupsAggregazioneRepository,
                               RuoloQualificaAssegnabilitaRepository ruoloQualificaAssegnabilitaRepository,
                               OimClient oimClient,
                               String utenteCancellazione,
                               String ufficioCancellazione,
                               Timestamp currentTimeStamp){
        this.groupsRepository=groupsRepository;
        this.groupsAggregazioneRepository=groupsAggregazioneRepository;
        this.ruoloQualificaAssegnabilitaRepository=ruoloQualificaAssegnabilitaRepository;
        this.oimClient=oimClient;
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

    @Override
    public Groups process(final Groups groups) {

        //CANCELLA RUOLO QUALIFICA DEL RUOLO LAVORATO
        ruoloQualificaAssegnabilitaRepository.deleteByName(groups.getNome().trim());

        List<GroupsAggregazione> groupsAggreg = groupsAggregazioneRepository.getAggregazioneByPrincipaleOrSecondaria(groups.getNome().trim());
        groupsAggreg.stream().forEach(groupsAggregazione -> {
            groupsAggregazione.setUtenteCancellazione(utenteCancellazione);
            groupsAggregazione.setUfficioCancellazione(ufficioCancellazione);
            groupsAggregazione.setDataCancellazione(currentTimeStamp);
        });
        groupsAggregazioneRepository.saveAll(groupsAggreg);
        //chiamo oim per cancellare il ruolo
        // oimClient.deleteRuoli(Arrays.asList(groups).stream().map(Groups::getNome).toList());
        return groups;
    }




}
