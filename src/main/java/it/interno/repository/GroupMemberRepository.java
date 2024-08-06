package it.interno.repository;

import it.interno.entity.GroupMembers;
import it.interno.entity.GroupMembersKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author mirco.cennamo on 05/08/2024
 * @project portale-gestioneapplicazioni-service-batch
 */
@Repository
@Component
public interface GroupMemberRepository extends JpaRepository<GroupMembers, GroupMembersKey> {
    @Query(
            value = "SELECT g.* FROM GROUPMEMBERS g WHERE g.APP_ID=?1 AND g.DATA_CAN IS NULL ORDER BY g.G_MEMBER DESC",
            countQuery = "SELECT count(*) FROM GROUPMEMBERS g WHERE g.APP_ID=?1 AND g.DATA_CAN IS  NULL",
            nativeQuery = true)
    Page<GroupMembers> findByAppId(String appId,Pageable pageable);

}
