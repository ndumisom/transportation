package za.com.cocamzansi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.LoginEntity;
import za.com.cocamzansi.entity.UserEntity;
import za.com.cocamzansi.model.ActionStatusLoginType;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.LoginModel;
import za.com.cocamzansi.repository.LoginRepository;
import za.com.cocamzansi.repository.UserRepository;
import za.com.cocamzansi.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse httpServletResponse;

    public ActionStatusType createLoginCredentials(LoginModel loginModel) {
        if (loginModel.getUsername() != null && loginModel.getPassword() != null && loginModel.getUserModel().getUserId().intValue() != 0) {
            LoginEntity loginEntity = new LoginEntity();
            loginEntity.setUsername(loginModel.getUsername());
            loginEntity.setPassword(loginModel.getPassword());
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(loginModel.getUserModel().getUserId());
            Optional<UserEntity> userEntity1 = this.userRepository.findById(userEntity.getUserId());
            loginEntity.setUserTypeEntityID(((UserEntity)userEntity1.get()).getTypeEntityId());
            loginEntity.setUserEntityId(userEntity);
            List<LoginEntity> loginEntities = this.loginRepository.findAll();
            Boolean isUserCredentialExists = Boolean.valueOf(false);
            for (LoginEntity loginEntity1 : loginEntities) {
                if (loginEntity1.getUsername().trim().equals(loginModel.getUsername().trim()))
                    isUserCredentialExists = Boolean.valueOf(true);
            }
            if (isUserCredentialExists.booleanValue())
                return new ActionStatusType(Boolean.valueOf(false), "Username.already.exists");
            this.loginRepository.save(loginEntity);
            return new ActionStatusType(Boolean.valueOf(false), "User.create.success");
        }
        return null;
    }

    public ActionStatusLoginType login(LoginModel loginModel) {
        if (loginModel.getUsername() != null && loginModel.getPassword() != null) {
            LoginEntity loginEntity = new LoginEntity();
            loginEntity.setUsername(loginModel.getUsername());
            loginEntity.setPassword(loginModel.getPassword());
            UserEntity userEntity = new UserEntity();
            loginEntity.setUserEntityId(userEntity);
            List<LoginEntity> loginEntities = this.loginRepository.findAll();
            Integer userType = Integer.valueOf(0);
            String username = null;
            Integer userIdD = Integer.valueOf(0);
            Integer userRole = Integer.valueOf(0);
            Boolean isUserCredentialExists = Boolean.valueOf(false);
            HttpSession session = null;
            for (LoginEntity loginEntity1 : loginEntities) {
                if (loginEntity1.getUsername().trim().equals(loginModel.getUsername().trim()) && loginEntity1
                        .getPassword().trim().equals(loginModel.getPassword().trim()) && loginEntity1.isEnabled()) {
                    isUserCredentialExists = Boolean.valueOf(true);
                    session = this.request.getSession(true);
                    session.setAttribute("usersession", loginEntity1.getUsername());
                    session.setAttribute("userId", loginEntity1);
                    userType = loginEntity1.getUserTypeEntityID().getTypeId();
                    username = loginEntity1.getUsername();
                    userIdD = loginEntity1.getUserEntityId().getUserId();
                    userRole = loginEntity1.getUserRole();
                    session.setAttribute("userId", userIdD);
                }
            }
            if (session == null) {
                System.out.println("Session was not set during login");
            } else {
                System.out.println("Session was set during login");
                System.out.println("Session was set during login :" + session.getAttribute("usersession"));
            }
            if (isUserCredentialExists.booleanValue()) {
                this.httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; path=/;expires=" + new Date((new Date()).getTime() + 60000000L) + ";SameSite=None; Secure");
                HttpServletResponse httpServletResponse = null;
                return new ActionStatusLoginType(Boolean.valueOf(true), "Username.login.successful", session.getId(), username, userType, userIdD ,httpServletResponse,userRole);
            }
            return new ActionStatusLoginType(Boolean.valueOf(false), "Wrong.creadentials");
        }
        return new ActionStatusLoginType(Boolean.valueOf(false), "Wrong.creadentials");
    }
}
