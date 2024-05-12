package za.com.cocamzansi.service;

import javax.servlet.http.HttpServletRequest;

public class UserSession {
    public Boolean isUserLoggedIn() {
        HttpServletRequest request = null;
        Boolean isUserLoggedIn = Boolean.valueOf(false);
        if (request != null && request.getSession().getAttribute("usersession") != null) {
            isUserLoggedIn = Boolean.valueOf(true);
            System.out.println(request.getSession().getAttribute("usersession") + ":Logged in ");
        }
        return isUserLoggedIn;
    }
}
