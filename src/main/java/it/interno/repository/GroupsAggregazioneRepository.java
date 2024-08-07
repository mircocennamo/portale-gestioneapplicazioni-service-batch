package it.interno.repository;


import it.interno.entity.GroupsAggregazione;
import it.interno.entity.GroupsAggregazioneKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupsAggregazioneRepository extends JpaRepository<GroupsAggregazione, GroupsAggregazioneKey> {



    @Query(value = "SELECT g.* " +
            "FROM  SSD_SECURITY.GROUPS_AGGREG g  WHERE ( TRIM(g.G_NAME_PRINC) =?1 OR TRIM(g.G_NAME_DIP)= ?1)  AND g.DATA_CAN IS NULL",
           nativeQuery = true)
    List<GroupsAggregazione> getAggregazioneByPrincipaleOrSecondaria(String ruoloPrincipale);

}
