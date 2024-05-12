package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.TelephoneEntity;

public interface TelephoneRepository extends JpaRepository<TelephoneEntity, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT u from TelephoneEntity u WHERE u.telephoneNumber = :telephoneNumber")
    List<TelephoneEntity> checkTelephoneExists(@Param("telephoneNumber") String paramString);
}
