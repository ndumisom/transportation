package za.com.cocamzansi.service;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.UserModel;

@Service
public interface UserService {
  ActionStatusType createUser(UserModel paramUserModel) throws IOException, MessagingException;

  UserModel getUser(Integer paramInteger);

  List<UserModel> getUsers();

  ActionStatusType delete(Integer paramInteger);

  List<UserModel> getUserByType(Integer paramInteger);

  List<UserModel> getUserByAddress(Integer paramInteger);

  List<UserModel> getUserByFullAddress(String paramString);
}
