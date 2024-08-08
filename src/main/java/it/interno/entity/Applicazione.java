package it.interno.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "SEC_APPLICAZIONE", schema = "SSD_SECURITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicazione {

    @Id
    @Column(name = "APP_ID")
    private String appId;
    @Column(name = "APP_NAME")
    private String appName;
    @Column(name = "APP_DESCRIPTION")
    private String appDescription;
    @Column(name = "APP_SCOPE")
    private String appScope;
    @Column(name = "APP_URL")
    private String appUrl;
    @Column(name = "APP_DATAINI")
    private LocalDate appDataIni;
    @Column(name = "APP_DATAFIN")
    private LocalDate appDataFin;
    @Column(name = "UTE_INS")
    private String utenteInserimento;
    @Column(name = "UFF_INS")
    private String ufficioInserimento;
    @Column(name = "DATE_INS")
    private Timestamp dataInserimento;
    @Column(name = "UTE_AGG")
    private String utenteAggiornamento;
    @Column(name = "UFF_AGG")
    private String ufficioAggiornamento;
    @Column(name = "DATE_AGG")
    private Timestamp dataAggiornamento;
    @Column(name = "UTE_CANC")
    private String utenteCancellazione;
    @Column(name = "UFF_CANC")
    private String ufficioCancellazione;
    @Column(name = "DATE_CANC")
    private Timestamp dataCancellazione;
    @Column(name = "ORDER_ID_CATALOGO")
    private Double idOrdineCatalogo;
    @Column(name = "ORDER_ID_AMBITO")
    private Integer idOrdineAmbito;
    @Column(name = "DATA_INIZIO_OPERATIVITA")
    private LocalDate dataInizioOperativita;
    @Column(name = "DATA_FINE_OPERATIVITA")
    private LocalDate dataFineOperativita;
    @Column(name = "SCADENZA_ACCESSO")
    private Integer scadenzaAccesso;
    @Column(name = "VISIBILITA_PORTALE")
    private Character visibilitaPortale;
    @Column(name = "VISIBILITA_CATALOGO")
    private Character visibilitaCatalogo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicazione that = (Applicazione) o;
        return getAppId().equals(that.getAppId()) && Objects.equals(getAppName(), that.getAppName()) && Objects.equals(getAppDescription(), that.getAppDescription()) && Objects.equals(getAppScope(), that.getAppScope()) && Objects.equals(getAppUrl(), that.getAppUrl()) && Objects.equals(getAppDataIni(), that.getAppDataIni()) && Objects.equals(getAppDataFin(), that.getAppDataFin()) && getIdOrdineCatalogo().equals(that.getIdOrdineCatalogo()) && getIdOrdineAmbito().equals(that.getIdOrdineAmbito()) && Objects.equals(getDataInizioOperativita(), that.getDataInizioOperativita()) && Objects.equals(getDataFineOperativita(), that.getDataFineOperativita()) && getScadenzaAccesso().equals(that.getScadenzaAccesso()) && getVisibilitaPortale().equals(that.getVisibilitaPortale()) && getVisibilitaCatalogo().equals(that.getVisibilitaCatalogo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppId(), getAppName(), getAppDescription(), getAppScope(), getAppUrl(), getAppDataIni(), getAppDataFin(), getIdOrdineCatalogo(), getIdOrdineAmbito(), getDataInizioOperativita(), getDataFineOperativita(), getScadenzaAccesso(), getVisibilitaPortale(), getVisibilitaCatalogo());
    }
}
