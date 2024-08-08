package it.interno.processor;

import it.interno.entity.ApplicMotivMembers;
import it.interno.repository.ApplicMotivMembersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;

@Slf4j
public class ApplicMotivMemberItemProcessor implements ItemProcessor<ApplicMotivMembers, ApplicMotivMembers> {


    private ApplicMotivMembersRepository applicMotivMembersRepository;

    private String utenteCancellazione;

     private String ufficioCancellazione;
     private Timestamp currentTimeStamp;


    public ApplicMotivMemberItemProcessor(ApplicMotivMembersRepository applicMotivMembersRepository,
                                          String utenteCancellazione, String ufficioCancellazione, Timestamp currentTimeStamp){
        this.applicMotivMembersRepository=applicMotivMembersRepository;
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

    @Override
    public ApplicMotivMembers process(final ApplicMotivMembers applicMotivMembers) {

        applicMotivMembers.setUtenteCancellazione(utenteCancellazione);
        applicMotivMembers.setUfficioCancellazione(ufficioCancellazione);
        applicMotivMembers.setDataCancellazione(currentTimeStamp);
        return applicMotivMembers;
    }


}
