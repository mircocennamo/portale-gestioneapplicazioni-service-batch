package it.interno.repository;


import it.interno.entity.Applicazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicazioneRepository extends JpaRepository<Applicazione, String> {

    @Query(value ="SELECT a.* FROM SSD_SECURITY.SEC_APPLICAZIONE a WHERE a.APP_ID = ?1 AND a.DATE_CANC IS NULL ORDER BY a.APP_ID DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_APPLICAZIONE a WHERE a.APP_ID = ?1 AND a.DATE_CANC IS NULL",
            nativeQuery = true)
    Page<Applicazione> findById(String idApplicazione, Pageable pageable);




}
