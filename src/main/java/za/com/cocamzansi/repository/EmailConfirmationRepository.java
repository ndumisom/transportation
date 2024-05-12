package za.com.cocamzansi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import za.com.cocamzansi.entity.EmailConfirmationEntity;

public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationEntity, Integer> {
    EmailConfirmationEntity getEmailConfirmationByConfirmationToken(@Param("confirmationToken") String paramString);
}
