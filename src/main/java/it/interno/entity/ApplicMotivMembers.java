package it.interno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "SEC_APPLIC_MOTIV_MEMBERS", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ApplicMotivMembersKey.class)
public class ApplicMotivMembers {

    @Id
    @Column(name = "G_MEMBER")
    private String codiceUtente;
    @Id
    @Column(name = "APP_ID")
    private String appId;
    @Id
    @Column(name = "ID_TIPO_MOTIVAZIONE")
    private Integer idTipoMotivazione;

    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "UTE_CANC")
    private String utenteCancellazione;
    @Column(name = "UFF_CANC")
    private String ufficioCancellazione;
    @Column(name = "DATE_CANC")
    private Timestamp dataCancellazione;

    public ApplicMotivMembers(String codiceUtente, String appId, Integer idTipoMotivazione, String utenteInserimento, String ufficioInserimento, Timestamp dataInserimento) {
        this.codiceUtente = codiceUtente;
        this.appId = appId;
        this.idTipoMotivazione = idTipoMotivazione;
        this.utenteInserimento = utenteInserimento;
        this.ufficioInserimento = ufficioInserimento;
        this.dataInserimento = dataInserimento;
    }
}
