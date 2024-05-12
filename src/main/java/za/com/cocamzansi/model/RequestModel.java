package za.com.cocamzansi.model;

import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.RequestAddressModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;

public class RequestModel {
    private Integer requestId;

    private UserModel UserModelFromId;

    private UserModel UserModelToId;

    private String requestMessage;

    private String createDateTime;

    private Integer status;

    private Integer userType;

    private String userFromFirstName;

    private String userToFirstName;

    private String userToProfilePicture;

    private String userFromProfilePicture;

    private String userToPhoneNumber;

    private String userFromPhoneNumber;

    private Double totalServicePrice;

    private GeoLocation geoLocation;

    private String distance;

    private String duration;

    private RequestAddressModel requestAddressModel;

    private AddressModel addressModel;

    private String fullAddress;

    public Integer getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public UserModel getUserModelFromId() {
        return this.UserModelFromId;
    }

    public void setUserModelFromId(UserModel userModelFromId) {
        this.UserModelFromId = userModelFromId;
    }

    public UserModel getUserModelToId() {
        return this.UserModelToId;
    }

    public void setUserModelToId(UserModel userModelToId) {
        this.UserModelToId = userModelToId;
    }

    public String getRequestMessage() {
        return this.requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserFromFirstName() {
        return this.userFromFirstName;
    }

    public void setUserFromFirstName(String userFromFirstName) {
        this.userFromFirstName = userFromFirstName;
    }

    public String getUserToFirstName() {
        return this.userToFirstName;
    }

    public void setUserToFirstName(String userToFirstName) {
        this.userToFirstName = userToFirstName;
    }

    public Double getTotalServicePrice() {
        return this.totalServicePrice;
    }

    public void setTotalServicePrice(Double totalServicePrice) {
        this.totalServicePrice = totalServicePrice;
    }

    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public RequestAddressModel getRequestAddressModel() {
        return this.requestAddressModel;
    }

    public void setRequestAddressModel(RequestAddressModel requestAddressModel) {
        this.requestAddressModel = requestAddressModel;
    }

    public AddressModel getAddressModel() {
        return this.addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public String getFullAddress() {
        return this.fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getUserToProfilePicture() {
        return this.userToProfilePicture;
    }

    public void setUserToProfilePicture(String userToProfilePicture) {
        this.userToProfilePicture = userToProfilePicture;
    }

    public String getUserFromProfilePicture() {
        return this.userFromProfilePicture;
    }

    public void setUserFromProfilePicture(String userFromProfilePicture) {
        this.userFromProfilePicture = userFromProfilePicture;
    }

    public String getUserToPhoneNumber() {
        return this.userToPhoneNumber;
    }

    public void setUserToPhoneNumber(String userToPhoneNumber) {
        this.userToPhoneNumber = userToPhoneNumber;
    }

    public String getUserFromPhoneNumber() {
        return this.userFromPhoneNumber;
    }

    public void setUserFromPhoneNumber(String userFromPhoneNumber) {
        this.userFromPhoneNumber = userFromPhoneNumber;
    }
}
