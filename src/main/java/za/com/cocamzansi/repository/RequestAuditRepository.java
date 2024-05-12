package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.RequestAuditEntity;
import za.com.cocamzansi.entity.UserEntity;

public interface RequestAuditRepository extends JpaRepository<RequestAuditEntity, Integer> {
    List<RequestAuditEntity> getRequestByRequestId(@Param("requestId") Integer paramInteger);

    @Transactional
    @Modifying
    @Query("SELECT u FROM RequestEntity u  WHERE u.userEntityFromId =:userEntityFromId order by createDateTime desc")
    List<RequestAuditEntity> findRequestByUserFromId(@Param("userEntityFromId") UserEntity paramUserEntity);

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=0 OR u.status=1 OR u.status=2 OR u.status=3 OR u.status=4  ORDER by u.requestId DESC")
    List<RequestAuditEntity> processRequest();

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=1 OR u.status=1 ORDER by u.requestId DESC")
    List<RequestAuditEntity> processRequestAccepted();

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=2")
    List<RequestAuditEntity> processRequestWaiting();

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=3")
    List<RequestAuditEntity> processRequestRejected();

    List<RequestAuditEntity> findByRequestTo(String paramString);

    List<RequestAuditEntity> findByRequestToOrderByRequestToDesc(String paramString);
}
