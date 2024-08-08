package it.interno.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Table(name = "SEC_APPLICAZIONE_MOTIVAZIONE", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ApplicazioneMotivazioneKey.class)
public class ApplicazioneMotivazione {

    @Id
    @Column(name = "APP_ID")
    private String idApp;
    @Id
    @Column(name = "ID_TIPO_MOTIVAZIONE")
    private Integer idTipoMotivazione;

    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "UTE_CANC")
    private String utentecancellazione;
    @Column(name = "DATE_CANC")
    private Timestamp dataCancellazione;

    @Column(name = "UFF_CANC")
    private String ufficioCancellazione;

    public ApplicazioneMotivazione(String idApp, Integer idTipoMotivazione, Timestamp dataInserimento, String utenteInserimento, String ufficioInserimento) {
        this.idApp = idApp;
        this.idTipoMotivazione = idTipoMotivazione;
        this.dataInserimento = dataInserimento;
        this.utenteInserimento = utenteInserimento;
        this.ufficioInserimento = ufficioInserimento;
    }

}
