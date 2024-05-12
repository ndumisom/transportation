package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.EmailAddressEntity;

@Repository
public interface EmailAddressRepository extends JpaRepository<EmailAddressEntity, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT u from EmailAddressEntity u WHERE u.emailAddress = :emailAddress")
    List<EmailAddressEntity> checkEmailAddressExists(@Param("emailAddress") String paramString);
}
