package it.interno.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GROUPS", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GroupsKey.class)
@DynamicUpdate
public class Groups {

    @Id
    @Column(name = "G_NAME")
    private String nome;
    @Id
    @Column(name = "G_APP")
    private String app;

    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "G_DESCRIPTION")
    private String descrizione;
    @Column(name = "G_GROUP")
    private String gruppo;
    @Column(name = "G_ID")
    private Integer id;
    @Column(name = "G_TYPE")
    private String tipo;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "UTE_AGG")
    private String utenteAggiornamento;
    @Column(name = "UFF_AGG")
    private String ufficioAggiornamento;
    @Column(name = "DATA_AGG")
    private Timestamp dataAggiornamento;
    @Column(name = "UTE_CAN")
    private String utenteCancellazione;
    @Column(name = "UFF_CAN")
    private String ufficioCancellazione;
    @Column(name = "DATA_CAN")
    private Timestamp dataCancellazione;
    @Column(name = "DATA_INIZIO_VALIDITA")
    private LocalDate dataInizioValidita;
    @Column(name = "DATA_FINE_VALIDITA")
    private LocalDate dataFineValidita;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SEC_RUOLO_QUALIF_ASSEGNABILITA",
            schema = "SSD_SECURITY",
            joinColumns = {
                    @JoinColumn(name = "G_NAME", referencedColumnName = "G_NAME"),
                    @JoinColumn(name = "G_APP", referencedColumnName = "G_APP")
            },
            inverseJoinColumns = @JoinColumn(name = "QUALIFICA_ASSEGNABILITA_ID")
    )
    private List<QualificaAssegnabilita> qualificheAssegnate;

    public Groups(String nome, String app) {
        this.nome = nome;
        this.app = app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return getNome().equals(groups.getNome()) && getApp().equals(groups.getApp()) && getDataInserimento().equals(groups.getDataInserimento()) && getDescrizione().equals(groups.getDescrizione()) && Objects.equals(getGruppo(), groups.getGruppo()) && Objects.equals(getId(), groups.getId()) && Objects.equals(getTipo(), groups.getTipo()) && Objects.equals(getUtenteInserimento(), groups.getUtenteInserimento()) && getDataInizioValidita().equals(groups.getDataInizioValidita()) && getDataFineValidita().equals(groups.getDataFineValidita());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getApp(), getDataInserimento(), getDescrizione(), getGruppo(), getId(), getTipo(), getUtenteInserimento(), getDataInizioValidita(), getDataFineValidita());
    }
}
