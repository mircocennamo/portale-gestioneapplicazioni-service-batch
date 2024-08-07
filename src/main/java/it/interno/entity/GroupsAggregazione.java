package it.interno.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "GROUPS_AGGREG", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GroupsAggregazioneKey.class)
public class GroupsAggregazione {

    @Id
    @Column(name = "G_NAME_PRINC")
    private String ruoloPrincipale;
    @Id
    @Column(name = "G_APP_PRINC")
    private String idAppPrincipale;
    @Id
    @Column(name = "G_NAME_DIP")
    private String ruoloDipendente;
    @Id
    @Column(name = "G_APP_DIP")
    private String idAppDipendente;

    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "DATA_CAN")
    private Timestamp dataCancellazione;
    @Column(name = "UTE_CAN")
    private String utenteCancellazione;
    @Column(name = "UFF_CAN")
    private String ufficioCancellazione;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsAggregazione that = (GroupsAggregazione) o;
        return getRuoloPrincipale().equals(that.getRuoloPrincipale()) && getIdAppPrincipale().equals(that.getIdAppPrincipale()) && getRuoloDipendente().equals(that.getRuoloDipendente()) && getIdAppDipendente().equals(that.getIdAppDipendente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRuoloPrincipale(), getIdAppPrincipale(), getRuoloDipendente(), getIdAppDipendente());
    }
}
