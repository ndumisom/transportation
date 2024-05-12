package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.AddressEntity;

@Repository
public interface AddressEntityReposity extends JpaRepository<AddressEntity, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT u from AddressEntity u WHERE u.streetNumber = :streetNumber AND u.streetName = :streetName AND  u.suburb = :suburb AND  u.postalcode = :postalcode")
    List<AddressEntity> checkAddressExists(@Param("streetNumber") Integer paramInteger1, @Param("streetName") String paramString1, @Param("suburb") String paramString2, @Param("postalcode") Integer paramInteger2);
}
