package za.com.cocamzansi.model;

import javax.servlet.http.HttpServletResponse;

public class ActionStatusLoginType {
    private Boolean success;

    private String message;

    private String userId;

    private String username;

    private Integer usertype;

    private Integer userIdD;

    private Integer userRole;

    private HttpServletResponse httpServletResponse;

    public ActionStatusLoginType() {}

    public ActionStatusLoginType(Boolean success) {
        this.success = success;
    }

    public ActionStatusLoginType(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ActionStatusLoginType(Boolean success, String message, String userId, String username, Integer usertype, Integer userIdD) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.usertype = usertype;
        this.username = username;
        this.userIdD = userIdD;
    }

    public ActionStatusLoginType(Boolean success, String message, String userId, String username, Integer usertype, Integer userIdD, HttpServletResponse httpServletResponse,Integer userRole) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.usertype = usertype;
        this.username = username;
        this.userIdD = userIdD;
        this.httpServletResponse = httpServletResponse;
        this.userRole = userRole;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUsertype() {
        return this.usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getUserIdD() {
        return this.userIdD;
    }

    public void setUserIdD(Integer userIdD) {
        this.userIdD = userIdD;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}
