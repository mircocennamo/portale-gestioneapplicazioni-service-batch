package it.interno.domain;

import lombok.*;

/**
 * @author mirco.cennamo on 12/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestDto implements java.io.Serializable{

    private String idApplicazione;

    private String utenteCancellazione;

    private String ufficioCancellazione;
    private String operation;


    private String status;


    private Long jobId;
}
