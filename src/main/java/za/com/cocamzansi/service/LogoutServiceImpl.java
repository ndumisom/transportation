package za.com.cocamzansi.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.LoginModel;
import za.com.cocamzansi.service.LogoutService;

@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    HttpServletRequest request;

    public ActionStatusType createLoginCredentials(LoginModel loginModel) {
        return null;
    }

    public ActionStatusType logout() {
        Object session = this.request.getSession().getAttribute("usersession");
        System.out.println(session);
        if (this.request != null && this.request.getSession().getAttribute("usersession") != null) {
            this.request.getSession().invalidate();
            return new ActionStatusType(Boolean.valueOf(true), "Username.logout.successful");
        }
        return new ActionStatusType(Boolean.valueOf(true), "Username.not.loggedin");
    }
}
