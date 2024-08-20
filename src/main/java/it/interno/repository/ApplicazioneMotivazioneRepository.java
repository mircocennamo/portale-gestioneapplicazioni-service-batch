package it.interno.repository;


import it.interno.entity.ApplicazioneMotivazione;
import it.interno.entity.ApplicazioneMotivazioneKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicazioneMotivazioneRepository extends JpaRepository<ApplicazioneMotivazione, ApplicazioneMotivazioneKey> {


    @Query(value ="SELECT m.* FROM SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE m WHERE m.APP_ID = ?1 AND m.DATE_CANC IS NULL ORDER BY m.ID_TIPO_MOTIVAZIONE DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE m WHERE m.APP_ID = ?1 AND m.DATE_CANC IS NULL",
            nativeQuery = true)
    Page<ApplicazioneMotivazione> findByIdApp(String idApp, Pageable pageable);


    @Query(value ="SELECT m.* FROM SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE m WHERE m.APP_ID = ?1  AND m.ID_TIPO_MOTIVAZIONE=?2 AND  m.DATE_CANC IS NULL ORDER BY m.ID_TIPO_MOTIVAZIONE DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_APPLICAZIONE_MOTIVAZIONE m WHERE m.APP_ID = ?1  AND m.ID_TIPO_MOTIVAZIONE=?2 AND  m.DATE_CANC IS NULL",
            nativeQuery = true)
    Page<ApplicazioneMotivazione> findById(String idApp,String tipoMotivazione, Pageable pageable);


}
