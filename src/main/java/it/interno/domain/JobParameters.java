package it.interno.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "campo applicationId non valorizzato")
    @Size(min = 1, message = "campo applicationId non valorizzato")
    private String applicationId;
    @NotNull(message = "campo utenteCancellazione non valorizzato")
    @Size(min = 1, message = "campo utenteCancellazione non valorizzato")
    private String utenteCancellazione;
    @NotNull(message = "campo ufficioCancellazione non valorizzato")
    @Size(min = 1, message = "campo ufficioCancellazione non valorizzato")
    private String ufficioCancellazione;
    @NotNull(message = "campo operation non valorizzato")
    @Size(min = 1, message = "campo operation non valorizzato")
    private String operation;

    private String nomeRuolo;



}
