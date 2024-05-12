package za.com.cocamzansi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.com.cocamzansi.entity.ServiceCategoryEntity;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, Integer> {}
