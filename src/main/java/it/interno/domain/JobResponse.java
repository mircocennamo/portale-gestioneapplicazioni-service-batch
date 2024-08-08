package it.interno.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author mirco.cennamo on 08/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobResponse implements Serializable {
    private Long jobId;


}
