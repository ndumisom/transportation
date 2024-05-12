package za.com.cocamzansi.entity;

import javax.persistence.*;

import za.com.cocamzansi.entity.AddressEntity;
import za.com.cocamzansi.entity.EmailAddressEntity;
import za.com.cocamzansi.entity.TelephoneEntity;
import za.com.cocamzansi.entity.UserTypeEntity;

import java.util.Date;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_profile_picture")
    private String userProfilePicture;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private UserTypeEntity typeEntityId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressId;

    @ManyToOne
    @JoinColumn(name = "email_address_id")
    public EmailAddressEntity emailAddressEntity;

    @ManyToOne
    @JoinColumn(name = "telephone_id")
    private TelephoneEntity telephoneId;

    private Date created;

    private Date updated;

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserTypeEntity getTypeEntityId() {
        return this.typeEntityId;
    }

    public void setTypeEntityId(UserTypeEntity typeEntityId) {
        this.typeEntityId = typeEntityId;
    }

    public AddressEntity getAddressId() {
        return this.addressId;
    }

    public void setAddressId(AddressEntity addressId) {
        this.addressId = addressId;
    }

    public void setEmailAddressEntity(EmailAddressEntity emailAddressEntity) {
        this.emailAddressEntity = emailAddressEntity;
    }

    public void setTelephoneId(TelephoneEntity telephoneId) {
        this.telephoneId = telephoneId;
    }

    public EmailAddressEntity getEmailAddressEntity() {
        return this.emailAddressEntity;
    }

    public TelephoneEntity getTelephoneId() {
        return this.telephoneId;
    }

    public String getUserProfilePicture() {
        return this.userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }
}
