package it.interno.repository;

import it.interno.entity.GroupMembers;
import it.interno.entity.GroupMembersKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mirco.cennamo on 05/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Repository
@Component
public interface GroupMemberRepository extends JpaRepository<GroupMembers, GroupMembersKey> {
    @Query(
            value = "SELECT g.* FROM SSD_SECURITY.GROUPMEMBERS g WHERE g.APP_ID=?1 AND g.DATA_CAN IS NULL ORDER BY g.G_MEMBER DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.GROUPMEMBERS g WHERE g.APP_ID=?1 AND g.DATA_CAN IS  NULL",
            nativeQuery = true)
    Page<GroupMembers> findByAppId(String appId,Pageable pageable);



    @Query(
            value = "SELECT g.* FROM SSD_SECURITY.GROUPMEMBERS g WHERE  g.G_NAME=?1   and  g.APP_ID=?2 AND g.DATA_CAN IS NULL ORDER BY g.G_MEMBER DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.GROUPMEMBERS g WHERE  g.G_NAME=?1   and  g.APP_ID=?2 AND g.DATA_CAN IS NULL",
            nativeQuery = true)
    Page<GroupMembers> getByRuolo(String codiceRuolo, String idApplicazione,Pageable pageable);


    @Query(value = "SELECT DISTINCT G_MEMBER FROM SSD_SECURITY.GROUPMEMBERS WHERE APP_ID = ?1 AND DATA_CAN IS NULL ORDER BY G_MEMBER DESC",
            countQuery = "SELECT COUNT(*) FROM (SELECT DISTINCT G_MEMBER FROM SSD_SECURITY.GROUPMEMBERS WHERE APP_ID = ?1 AND DATA_CAN IS NULL)",
            nativeQuery = true)
    Page<String> getUtentiDistintiByApp(String idApp,Pageable pageable);

    @Query(value = "SELECT g.* FROM SSD_SECURITY.GROUPMEMBERS g  WHERE TRIM(g.G_MEMBER) = TRIM(?1)  AND g.APP_ID = ?2   AND G_NAME NOT IN (?3)  AND g.DATA_CAN IS NULL",
            nativeQuery = true)
    List<GroupMembers> getByUtenteAndAppNotInCodiceRuolo(String codiceUtente, String idApplicazione, String... codiceRuolo);

    @Query(
            value = "SELECT g.* FROM SSD_SECURITY.GROUPMEMBERS g WHERE g.APP_ID=?1 AND g.DATA_CAN IS NULL ORDER BY g.G_MEMBER DESC",
            nativeQuery = true)
    List<GroupMembers> findByAppId(String appId);



}
