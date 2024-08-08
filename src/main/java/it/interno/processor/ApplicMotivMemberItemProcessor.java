package it.interno.processor;

import it.interno.entity.ApplicMotivMembers;
import it.interno.repository.ApplicMotivMembersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;

@Slf4j
public class ApplicMotivMemberItemProcessor implements ItemProcessor<ApplicMotivMembers, ApplicMotivMembers> {




    private String utenteCancellazione;

     private String ufficioCancellazione;
     private Timestamp currentTimeStamp;


    public ApplicMotivMemberItemProcessor(String utenteCancellazione, String ufficioCancellazione, Timestamp currentTimeStamp){
        this.utenteCancellazione=utenteCancellazione;
        this.ufficioCancellazione=ufficioCancellazione;
        this.currentTimeStamp=currentTimeStamp;
    }

    @Override
    public ApplicMotivMembers process(final ApplicMotivMembers applicMotivMembers) {


        return applicMotivMembers;
    }


}
