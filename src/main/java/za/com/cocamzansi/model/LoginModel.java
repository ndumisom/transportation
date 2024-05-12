package za.com.cocamzansi.model;

import za.com.cocamzansi.model.UserModel;

public class LoginModel {
    private Integer loginId;

    private String username;

    private String password;

    private UserModel userModel;

    public Integer getLoginId() {
        return this.loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel getUserModel() {
        return this.userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
