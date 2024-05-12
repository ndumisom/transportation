package za.com.cocamzansi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import za.com.cocamzansi.entity.UserEntity;

@Entity
@Table(name = "emailconfirmation")
public class EmailConfirmationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirmemail_id")
    private Integer confirmemailId;

    @Column(name = "email")
    private String email;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public Integer getConfirmemailId() {
        return this.confirmemailId;
    }

    public void setConfirmemailId(Integer confirmemailId) {
        this.confirmemailId = confirmemailId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationToken() {
        return this.confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
