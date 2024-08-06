package it.interno.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupMembersKey implements Serializable {
    private String nomeUtente;
    private String nomeRuolo;
}
