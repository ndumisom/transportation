package za.com.cocamzansi.service;

import za.com.cocamzansi.model.ActionStatusLoginType;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.LoginModel;

public interface LoginService {
    ActionStatusType createLoginCredentials(LoginModel paramLoginModel);

    ActionStatusLoginType login(LoginModel paramLoginModel);
}
