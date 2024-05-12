package za.com.cocamzansi.service;

import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.LoginModel;

public interface LogoutService {
    ActionStatusType createLoginCredentials(LoginModel paramLoginModel);

    ActionStatusType logout();
}
