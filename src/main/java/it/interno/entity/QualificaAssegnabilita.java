package it.interno.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SEC_QUALIFICA_ASSEGNABILITA", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificaAssegnabilita {

    @Id
    @Column(name = "QUALIFICA_ASSEGNABILITA_ID")
    private Integer id;

    @Column(name = "QUALIFICA_ASSEGNABILITA")
    private String descrizioneQualifica;

    public QualificaAssegnabilita(Integer id) {
        this.id = id;
    }
}
