package za.com.cocamzansi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.EmailAddressModel;
import za.com.cocamzansi.model.LoginModel;
import za.com.cocamzansi.model.TelephoneModel;
import za.com.cocamzansi.model.UserTypeModel;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class UserModel {
    private Integer userId;

    private String firstName;

    private String lastName;

    private String userProfilePicture;

    private AddressModel addressModel;

    private UserTypeModel typeModel;

    private ActionStatusType actionStatusType;

    private EmailAddressModel emailAddressModel;

    private TelephoneModel telephoneModel;

    private LoginModel loginModel;

    private Date created;

    public UserModel() {}

    public UserModel(ActionStatusType actionStatusType) {
        this.actionStatusType = actionStatusType;
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

    public AddressModel getAddressModel() {
        return this.addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public UserTypeModel getTypeModel() {
        return this.typeModel;
    }

    public void setTypeModel(UserTypeModel typeModel) {
        this.typeModel = typeModel;
    }

    public ActionStatusType getActionStatusType() {
        return this.actionStatusType;
    }

    public void setActionStatusType(ActionStatusType actionStatusType) {
        this.actionStatusType = actionStatusType;
    }

    public EmailAddressModel getEmailAddressModel() {
        return this.emailAddressModel;
    }

    public void setEmailAddressModel(EmailAddressModel emailAddressModel) {
        this.emailAddressModel = emailAddressModel;
    }

    public TelephoneModel getTelephoneModel() {
        return this.telephoneModel;
    }

    public void setTelephoneModel(TelephoneModel telephoneModel) {
        this.telephoneModel = telephoneModel;
    }

    public LoginModel getLoginModel() {
        return this.loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public String getUserProfilePicture() {
        return this.userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String toString() {
        return "UserModel{userId=" + this.userId + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", userProfilePicture='" + this.userProfilePicture + '\'' + ", addressModel=" + this.addressModel + ", typeModel=" + this.typeModel + ", actionStatusType=" + this.actionStatusType + '}';
    }
}
