package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.com.cocamzansi.entity.DurationEntity;
import za.com.cocamzansi.entity.RequestAuditEntity;
import za.com.cocamzansi.entity.UserEntity;

@Repository
public interface DurationRepository extends JpaRepository<DurationEntity, Integer> {
    List<DurationEntity> findByRequestAuditId(@Param("requestAuditId") RequestAuditEntity paramRequestAuditEntity);

    List<DurationEntity> findByUserId(@Param("userId") UserEntity paramUserEntity);

    List<DurationEntity> findByUserIdOrderByDurationIdDesc(@Param("userId") UserEntity paramUserEntity);

    List<DurationEntity> findByRequestAuditIdAndUserId(@Param("requestAuditId") RequestAuditEntity paramRequestAuditEntity, @Param("userId") UserEntity paramUserEntity);
}
