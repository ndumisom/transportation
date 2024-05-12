package za.com.cocamzansi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.com.cocamzansi.entity.RequestEntity;

public interface ReloadRepository extends JpaRepository<RequestEntity, Integer> {}
