package it.interno.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "GROUPMEMBERS", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GroupMembersKey.class)
@DynamicUpdate
public class GroupMembers {

    @Id
    @Column(name = "G_MEMBER")
    private String nomeUtente;
    @Id
    @Column(name = "G_NAME")
    private String nomeRuolo;

    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "APP_ID")
    private String appId;
    @Column(name = "UTE_CAN")
    private String utenteCancellazione;
    @Column(name = "DATA_CAN")
    private Timestamp dataCancellazione;
    @Column(name = "UFF_CAN")
    private String ufficioCancellazione;

    public GroupMembers(String nomeUtente, String nomeRuolo) {
        this.nomeUtente = nomeUtente;
        this.nomeRuolo = nomeRuolo;
    }

    public GroupMembers(String nomeUtente, String nomeRuolo, String utenteInserimento, Timestamp dataInserimento, String ufficioInserimento, String appId) {
        this.nomeUtente = nomeUtente;
        this.nomeRuolo = nomeRuolo;
        this.utenteInserimento = utenteInserimento;
        this.dataInserimento = dataInserimento;
        this.ufficioInserimento = ufficioInserimento;
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMembers that = (GroupMembers) o;
        return getNomeUtente().equals(that.getNomeUtente()) && getNomeRuolo().equals(that.getNomeRuolo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNomeUtente(), getNomeRuolo());
    }
}
