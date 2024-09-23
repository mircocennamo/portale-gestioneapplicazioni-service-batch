package it.interno.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 * @author mirco.cennamo on 06/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Data
@ToString
public class JobParameters {


    @NotNull(message = "campo requestId non valorizzato")
    @Size(min = 1, message = "campo requestId non valorizzato")
    private String requestId;





    @NotNull(message = "campo utente non valorizzato")
    @Size(min = 1, message = "campo utente non valorizzato")
    private String utente;


    @NotNull(message = "campo ufficio non valorizzato")
    @Size(min = 1, message = "campo ufficio non valorizzato")
    private String ufficio;

    @NotNull(message = "campo operation non valorizzato")
    @Size(min = 1, message = "campo operation non valorizzato")
    private String operation;

    @NotNull(message = "campo currentDate non valorizzato")
    private String currentDate;

    private String applicationId;

    private String nomeRuolo;

    //parametri per la cancellazione di un group aggragazione
    private String appPrincipale;
    private String ruoloPrincipale;
    private String appDipendente;
    private String ruoloDipendente;

    private String tipoMotivazioneId;



}
