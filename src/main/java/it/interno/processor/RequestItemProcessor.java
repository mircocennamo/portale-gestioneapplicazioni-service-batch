package it.interno.processor;

import it.interno.entity.Request;
import it.interno.repository.GroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestItemProcessor implements ItemProcessor<Request, Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestItemProcessor.class);

    @Autowired
    GroupMemberRepository groupMemberRepository;

    private long jobExecutionId;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecutionId = stepExecution.getJobExecutionId();
    }

    @Override
    public Request process(final Request request)  {

        request.setJobId(jobExecutionId);
        //-------------------------------------------------------------------------------

        LOGGER.info("set request {} jobExecutionId {} ", request, jobExecutionId);
/*try{
    Thread.sleep(20000);
}catch (InterruptedException e){
    System.out.println(e);
}

 */



         return request;
    }




}
