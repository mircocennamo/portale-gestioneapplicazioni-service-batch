package it.interno.domain;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Data
@ToString
public class JobParameters {

    private String applicationId;
    private String utenteCancellazione;
    private String ufficioCancellazione;


}
