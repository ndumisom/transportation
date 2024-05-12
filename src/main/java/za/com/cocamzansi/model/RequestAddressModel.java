package za.com.cocamzansi.model;

public class RequestAddressModel {
    private String userId;

    private String firstName;

    private String lastName;

    private String duration;

    private String distnce;

    private String originalAddress;

    private String suburb;

    private String userType;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
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

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistnce() {
        return this.distnce;
    }

    public void setDistnce(String distnce) {
        this.distnce = distnce;
    }

    public String getOriginalAddress() {
        return this.originalAddress;
    }

    public void setOriginalAddress(String originalAddress) {
        this.originalAddress = originalAddress;
    }

    public String getSuburb() {
        return this.suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
