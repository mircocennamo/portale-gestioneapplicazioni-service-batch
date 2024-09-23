package it.interno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "REQUEST",schema = "SSD_SECURITY")

public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;


    @Column(name = "IDAPPLICAZIONE")
    private String idApplicazione;
    @Column(name = "UTENTE")
    private String utente;
    @Column(name = "UFFICIO")
    private String ufficio;
    @Column(name = "OPERATION")
    private String operation;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "JOB_ID")
    private Long jobId;


    //@JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "GROUPUPDATE")
    private String groupUpdate;

    @Column(name = "date")
   private String currentTimeStamp;

    @Column(name = "TIPOMOTIVAZIONEID")
    private String tipoMotivazioneId;

    @Column(name = "NOMERUOLO")
    private String nomeRuolo;


   // @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "REGOLESICUREZZAUPDATE",length = 1000)
    private String regoleSicurezzaUpdate;

}
