package it.interno.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupsAggregazioneKey implements Serializable {
    private String ruoloPrincipale;
    private String idAppPrincipale;
    private String ruoloDipendente;
    private String idAppDipendente;
}
