package za.com.cocamzansi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "login_id")
    private Integer loginId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntityId;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserTypeEntity userTypeEntityID;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "user_role")
    private Integer userRole;

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

    public UserEntity getUserEntityId() {
        return this.userEntityId;
    }

    public void setUserEntityId(UserEntity userEntityId) {
        this.userEntityId = userEntityId;
    }

    public UserTypeEntity getUserTypeEntityID() {
        return this.userTypeEntityID;
    }

    public void setUserTypeEntityID(UserTypeEntity userTypeEntityID) {
        this.userTypeEntityID = userTypeEntityID;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}
