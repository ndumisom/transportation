package za.com.cocamzansi.model;

import java.util.Date;
import java.util.Map;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.UserModel;

public class DurationModel {
    private Integer durationId;

    RequestModel requestId;

    UserModel userEntity;

    private String status;

    private Date created;

    private Date updated;

    Map<String, Long> calculateTimeDifference;

    String hoursWorked;

    String serviceType;

    public Integer getDurationId() {
        return this.durationId;
    }

    public void setDurationId(Integer durationId) {
        this.durationId = durationId;
    }

    public RequestModel getRequestId() {
        return this.requestId;
    }

    public void setRequestId(RequestModel requestId) {
        this.requestId = requestId;
    }

    public UserModel getUserEntity() {
        return this.userEntity;
    }

    public void setUserEntity(UserModel userEntity) {
        this.userEntity = userEntity;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Map<String, Long> getCalculateTimeDifference() {
        return this.calculateTimeDifference;
    }

    public void setCalculateTimeDifference(Map<String, Long> calculateTimeDifference) {
        this.calculateTimeDifference = calculateTimeDifference;
    }

    public String getHoursWorked() {
        return this.hoursWorked;
    }

    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
