package za.com.cocamzansi.service;

import java.util.List;
import za.com.cocamzansi.model.UserTypeModel;

public interface UserTypeService {
    List<UserTypeModel> getUserByType();
}
