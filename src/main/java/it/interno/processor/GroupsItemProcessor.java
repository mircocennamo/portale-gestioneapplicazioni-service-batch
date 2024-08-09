package it.interno.processor;

import it.interno.entity.Groups;
import it.interno.entity.GroupsAggregazione;
import it.interno.repository.GroupsAggregazioneRepository;
import it.interno.repository.RuoloQualificaAssegnabilitaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class GroupsItemProcessor implements ItemProcessor<Groups, Groups> {


    private GroupsAggregazioneRepository groupsAggregazioneRepository;
    private RuoloQualificaAssegnabilitaRepository ruoloQualificaAssegnabilitaRepository;
    private String utenteCancellazione;
    private String ufficioCancellazione;
    private Timestamp currentTimeStamp;


    public GroupsItemProcessor(
                               GroupsAggregazioneRepository groupsAggregazioneRepository,
                               RuoloQualificaAssegnabilitaRepository ruoloQualificaAssegnabilitaRepository,
                               String utenteCancellazione,
                               String ufficioCancellazione,
                               Timestamp currentTimeStamp){

        this.groupsAggregazioneRepository=groupsAggregazioneRepository;
        this.ruoloQualificaAssegnabilitaRepository=ruoloQualificaAssegnabilitaRepository;

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



        groups.setDataCancellazione(currentTimeStamp);
        groups.setUtenteCancellazione(utenteCancellazione);
        groups.setUfficioCancellazione(ufficioCancellazione);
        return groups;
    }




}
