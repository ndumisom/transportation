package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.ServiceEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT u FROM ServiceEntity u  WHERE 1 = 1")
    List<ServiceEntity> findServices();
}
