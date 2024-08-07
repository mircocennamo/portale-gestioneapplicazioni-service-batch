package it.interno.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "SEC_REGOLE_SICUREZZA", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RegolaSicurezzaKey.class)
public class RegolaSicurezza {

    @Id
    @Column(name = "G_NAME")
    private String ruolo;
    @Id
    @Column(name = "APP_ID")
    private String appId;
    @Id
    @Column(name = "ID_BLOCCO_REGOLA")
    private Integer numeroRegola;
    @Id
    @Column(name = "ID_PRG_BLOCCO_REGOLA")
    private Integer progressivoRegola;

    @Column(name = "ID_GRUPPO_DI_LAVORO")
    private Integer idGruppoLavoro;
    @Column(name = "ID_ENTE")
    private Integer idEnte;
    @Column(name = "REGIONE")
    private String regione;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "COMUNE")
    private String comune;
    @Column(name = "COD_UFF")
    private String codiceUfficio;
    @Column(name = "ID_FUNZIONE")
    private String idFunzione;
    @Column(name = "RUOLO")
    private String ruoloQualifica;

    @Id
    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "DATE_CAN")
    private Timestamp dataCancellazione;
    @Column(name = "UFF_CAN")
    private String ufficioCancellazione;
    @Column(name = "UTE_CAN")
    private String utenteCancellazione;
    @Column(name = "NOME_REGOLA_SICUREZZA")
    private String nomeRegola;
    @Column(name = "TIPO_REGOLA")
    private String tipoRegola;

    public RegolaSicurezza(String ruolo, String appId, Integer numeroRegola, Integer progressivoRegola, String nomeRegola, String tipoRegola) {
        this.ruolo = ruolo;
        this.appId = appId;
        this.numeroRegola = numeroRegola;
        this.progressivoRegola = progressivoRegola;
        this.nomeRegola = nomeRegola;
        this.tipoRegola = tipoRegola;
    }

    public RegolaSicurezza(String ruolo, String appId, Integer numeroRegola, Integer progressivoRegola, Integer idGruppoLavoro, Integer idEnte, String regione, String provincia, String comune, String codiceUfficio, String idFunzione, String ruoloQualifica, String nomeRegola, String tipoRegola) {
        this.ruolo = ruolo;
        this.appId = appId;
        this.numeroRegola = numeroRegola;
        this.progressivoRegola = progressivoRegola;
        this.idGruppoLavoro = idGruppoLavoro;
        this.idEnte = idEnte;
        this.regione = regione;
        this.provincia = provincia;
        this.comune = comune;
        this.codiceUfficio = codiceUfficio;
        this.idFunzione = idFunzione;
        this.ruoloQualifica = ruoloQualifica;
        this.nomeRegola = nomeRegola;
        this.tipoRegola = tipoRegola;
    }

    public RegolaSicurezza(String ruolo, String appId, Integer numeroRegola, Integer progressivoRegola, Integer idGruppoLavoro, Integer idEnte, String regione, String provincia, String comune, String codiceUfficio, String idFunzione, String ruoloQualifica, String nomeRegola, String tipoRegola, String utenteInserimento, String ufficioInserimento, Timestamp dataInserimento) {
        this.ruolo = ruolo;
        this.appId = appId;
        this.numeroRegola = numeroRegola;
        this.progressivoRegola = progressivoRegola;
        this.idGruppoLavoro = idGruppoLavoro;
        this.idEnte = idEnte;
        this.regione = regione;
        this.provincia = provincia;
        this.comune = comune;
        this.codiceUfficio = codiceUfficio;
        this.idFunzione = idFunzione;
        this.ruoloQualifica = ruoloQualifica;
        this.nomeRegola = nomeRegola;
        this.tipoRegola = tipoRegola;
        this.utenteInserimento = utenteInserimento;
        this.ufficioInserimento = ufficioInserimento;
        this.dataInserimento = dataInserimento;
    }
}
