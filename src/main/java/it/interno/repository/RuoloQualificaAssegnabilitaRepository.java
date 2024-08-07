package it.interno.repository;


import it.interno.entity.RuoloQualificaAssegnabilita;
import it.interno.entity.RuoloQualificaAssegnabilitaKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloQualificaAssegnabilitaRepository extends JpaRepository<RuoloQualificaAssegnabilita, RuoloQualificaAssegnabilitaKey> {

    long deleteByName(String ruolo);




}
