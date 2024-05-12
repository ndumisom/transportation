package za.com.cocamzansi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.com.cocamzansi.entity.LoginEntity;
import za.com.cocamzansi.entity.UserEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT u FROM LoginEntity u  WHERE u.username =:username AND u.password =:password")
    List<UserEntity> loginUsernamPassword(@Param("username") String paramString1, @Param("password") String paramString2);

    @Transactional
    @Modifying
    @Query("SELECT u FROM LoginEntity u  WHERE u.userEntityId =:userEntityId")
    List<LoginEntity> findByUserId(@Param("userEntityId") UserEntity paramUserEntity);
}
