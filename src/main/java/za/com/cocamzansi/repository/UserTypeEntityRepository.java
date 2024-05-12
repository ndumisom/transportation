package za.com.cocamzansi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.com.cocamzansi.entity.UserTypeEntity;

@Repository
public interface UserTypeEntityRepository extends JpaRepository<UserTypeEntity, Integer> {}
