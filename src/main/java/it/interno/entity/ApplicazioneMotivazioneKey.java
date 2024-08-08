package it.interno.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicazioneMotivazioneKey implements Serializable {
    private String idApp;
    private Integer idTipoMotivazione;
}
