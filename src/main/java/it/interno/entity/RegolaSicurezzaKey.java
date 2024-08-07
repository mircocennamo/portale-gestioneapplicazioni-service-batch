package it.interno.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegolaSicurezzaKey implements Serializable {
    private String ruolo;
    private String appId;
    private Integer numeroRegola;
    private Integer progressivoRegola;
    private Timestamp dataInserimento;
}
