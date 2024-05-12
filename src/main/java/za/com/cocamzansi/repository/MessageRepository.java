package za.com.cocamzansi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.com.cocamzansi.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {}
