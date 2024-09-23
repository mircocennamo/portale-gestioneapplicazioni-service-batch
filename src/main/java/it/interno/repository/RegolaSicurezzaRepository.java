package it.interno.repository;


import it.interno.entity.RegolaSicurezza;
import it.interno.entity.RegolaSicurezzaKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RegolaSicurezzaRepository extends JpaRepository<RegolaSicurezza, RegolaSicurezzaKey> {



    @Query(value = "SELECT r.* FROM SSD_SECURITY.SEC_REGOLE_SICUREZZA r WHERE  r.APP_ID = ?1 AND r.DATE_CAN IS NULL ORDER BY r.G_NAME DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_REGOLE_SICUREZZA r WHERE  r.APP_ID = ?1 AND r.DATE_CAN IS NULL",
            nativeQuery = true)
    Page<RegolaSicurezza> getRegoleByAppId(String appId, Pageable pageable);

    @Query(value ="SELECT r.* FROM SSD_SECURITY.SEC_REGOLE_SICUREZZA r WHERE r.G_NAME = ?1 AND r.APP_ID = ?2 AND r.DATE_CAN IS NULL ORDER BY r.G_NAME DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_REGOLE_SICUREZZA r WHERE r.G_NAME = ?1 AND r.APP_ID = ?2 AND r.DATE_CAN IS NULL",
            nativeQuery = true)
    Page<RegolaSicurezza> getRegoleByNomeRuoloAndAppId(String nomeRuolo, String appId, Pageable pageable);

    @Query(value = "SELECT SSD_SECURITY.IS_RUOLO_APPLICATIVO_ASSEGNABILE(:codiceUtente, :idApp, :codiceRuolo) FROM DUAL", nativeQuery = true)
    Integer isRuoloApplicativoAssegnabile(String codiceUtente, String idApp, String codiceRuolo);

    @Query(value ="SELECT r.* FROM SSD_SECURITY.SEC_REGOLE_SICUREZZA r WHERE r.G_NAME = ?1 AND r.APP_ID = ?2 AND r.ID_BLOCCO_REGOLA = ?3 AND r.DATE_CAN IS NULL",
            nativeQuery = true)
    List<RegolaSicurezza> getRegoleByNomeRuoloAndAppIdAndIdRegola(String nomeRuolo, String appId, Integer idRegola);

}
