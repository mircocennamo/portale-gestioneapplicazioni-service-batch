package it.interno.repository;

import it.interno.entity.Request;
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
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(
            value = "SELECT * FROM SSD_SECURITY.REQUEST r WHERE r.status=?1 and r.IDAPPLICAZIONE=?2  and r.OPERATION=?3 ORDER BY r.ID DESC",
            countQuery = "SELECT count(*) FROM SSD_SECURITY.REQUEST r WHERE r.status=?1 and r.IDAPPLICAZIONE=?2  and r.OPERATION=?3 ",
            nativeQuery = true)
    Page<Request> findRequestByStatusAndIdAppAndOperation(String status,String applicationId,String operation, Pageable pageable);

    @Query(
            value = "SELECT * FROM SSD_SECURITY.REQUEST r WHERE r.status=?1 AND r.OPERATION=?2 ORDER BY r.ID DESC",
            nativeQuery = true)
    List<Request> findRequestByStatusAndOperation(String status, String operation);

    List<Request> findRequestByOperation(String operation);

    List<Request> findRequestByStatus(String status);
}
