package za.com.cocamzansi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ActionStatusType {
    private Boolean success;

    private String message;

    @JsonIgnore
    private String businessId;

    public ActionStatusType() {}

    public ActionStatusType(Boolean success) {
        this.success = success;
    }

    public ActionStatusType(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ActionStatusType(Boolean success, String message, String businessId) {
        this.success = success;
        this.message = message;
        this.businessId = businessId;
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

    public String getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String toString() {
        return "ActionStatusType{success=" + this.success + ", message='" + this.message + '\'' + ", businessId='" + this.businessId + '\'' + '}';
    }
}
