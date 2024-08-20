package it.interno.repository;


import it.interno.entity.ApplicMotivMembers;
import it.interno.entity.ApplicMotivMembersKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicMotivMembersRepository extends JpaRepository<ApplicMotivMembers, ApplicMotivMembersKey> {




    @Query(value ="SELECT member.* FROM SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS member WHERE member.APP_ID = ?1 AND member.DATE_CANC IS NULL ORDER BY member.G_MEMBER DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS member WHERE member.APP_ID = ?1 AND member.DATE_CANC IS NULL",
            nativeQuery = true)
    Page<ApplicMotivMembers> getByApp(String appId, Pageable pageable);


    @Query(value ="SELECT a.* FROM SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS a WHERE a.G_MEMBER = ?1 AND a.APP_ID = ?2 AND a.DATE_CANC IS NULL",nativeQuery = true)
    List<ApplicMotivMembers> getByUtenteEApp(String codiceUtente, String appId);

    @Query(value ="SELECT a.* FROM SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS a WHERE  a.APP_ID = ?1 AND a.ID_TIPO_MOTIVAZIONE = ?2 AND a.DATE_CANC IS NULL ORDER BY a.APP_ID DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.SEC_APPLIC_MOTIV_MEMBERS a WHERE  a.APP_ID = ?1 AND a.ID_TIPO_MOTIVAZIONE = ?2 AND a.DATE_CANC IS NULL",
            nativeQuery = true)
    Page<ApplicMotivMembers> getByIdAppAndIdTipoMotivazione(String idApp, String idTipoMotivazione, Pageable pageable);



}
