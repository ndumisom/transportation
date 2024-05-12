package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.AddressEntity;
import za.com.cocamzansi.entity.EmailAddressEntity;
import za.com.cocamzansi.entity.UserEntity;
import za.com.cocamzansi.entity.UserTypeEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByFirstNameAndLastName(@Param("firstName") String paramString1, @Param("lastName") String paramString2);

    @Transactional
    @Modifying
    @Query("SELECT u FROM UserEntity u  WHERE u.typeEntityId = :typeEntityId")
    List<UserEntity> findAllByUserType(@Param("typeEntityId") UserTypeEntity paramUserTypeEntity);

    @Transactional
    @Modifying
    @Query("SELECT u FROM UserEntity u  WHERE u.addressId = :addressId")
    List<UserEntity> findAllByUserAddress(@Param("addressId") AddressEntity paramAddressEntity);

    @Transactional
    @Modifying
    @Query("SELECT u FROM UserEntity u  WHERE u.emailAddressEntity = :emailAddressEntity")
    List<UserEntity> findAllByUserEmailAddressEntity(@Param("emailAddressEntity") EmailAddressEntity paramEmailAddressEntity);

    UserEntity findByUserId(@Param("userId") Integer paramInteger);
}
