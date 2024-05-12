package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.entity.UserEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT u FROM RequestEntity u  WHERE u.userEntityFromId =:userEntityFromId order by createDateTime desc")
    List<RequestEntity> findRequestByUserFromId(@Param("userEntityFromId") UserEntity paramUserEntity);

    RequestEntity getRequestByRequestId(@Param("requestId") Integer paramInteger);

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=0 OR u.status=1 OR u.status=2 OR u.status=3 OR u.status=4  ORDER by u.requestId DESC")
    List<RequestEntity> processRequest();

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=1 OR u.status=1 ORDER by u.requestId DESC")
    List<RequestEntity> processRequestAccepted();

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=2")
    List<RequestEntity> processRequestWaiting();

    @Transactional
    @Modifying
    @Query("SELECT u from RequestEntity u WHERE u.status=3")
    List<RequestEntity> processRequestRejected();
}
