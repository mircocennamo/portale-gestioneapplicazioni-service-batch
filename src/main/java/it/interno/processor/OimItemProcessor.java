package it.interno.processor;

import it.interno.client.OimClient;
import it.interno.entity.Groups;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class OimItemProcessor implements ItemProcessor<Groups, Groups> {

   // @Autowired
   // private OimClient oimClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(OimItemProcessor.class);


    OimClient oimClient;

    private String appId;





    public OimItemProcessor(OimClient oimClient,String appId){
        this.oimClient=oimClient;
        this.appId=appId;
    }

    @Override
    public Groups process(final Groups group) {
        //chiamo oim per cancellare l'utente dal gruppo

       if("13".equals(appId)){
           throw new RuntimeException("Simulo errore da oim");
       }

        // oimClient.deleteRuoli(Arrays.asList(group).stream().map(Groups::getNome).toList());

      return group;
    }




}
