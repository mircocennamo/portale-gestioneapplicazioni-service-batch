package it.interno.repository;


import it.interno.entity.Groups;
import it.interno.entity.GroupsKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupsRepository extends JpaRepository<Groups, GroupsKey> {

    @Query(value ="SELECT g.* FROM SSD_SECURITY.GROUPS g WHERE g.G_APP = ?1 AND g.DATA_CAN IS NULL ORDER BY g.G_NAME DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.GROUPS g WHERE g.G_APP = ?1 AND g.DATA_CAN IS NULL",
            nativeQuery = true)
    Page<Groups> findAllByAppId(String appId, Pageable pageable);


}

