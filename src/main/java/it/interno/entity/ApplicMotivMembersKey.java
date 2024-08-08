package it.interno.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicMotivMembersKey implements Serializable {
    private String codiceUtente;
    private String appId;
    private Integer idTipoMotivazione;
}
