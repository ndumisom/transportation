package za.com.cocamzansi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import za.com.cocamzansi.entity.UserEntity;

@Entity
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private Integer requestId;

    @ManyToOne
    @JoinColumn(name = "request_from")
    private UserEntity userEntityFromId;

    @Column(name = "request_to")
    private String requestTo;

    @Column(name = "status")
    private Integer status = Integer.valueOf(0);

    @Column(name = "request_message")
    private String requestMessage;

    @CreationTimestamp
    private Date createDateTime;

    @UpdateTimestamp
    private Date updateDateTime;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "latitute")
    private String latitute;

    @Column(name = "longitute")
    private String longitute;

    public Integer getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public UserEntity getUserEntityFromId() {
        return this.userEntityFromId;
    }

    public void setUserEntityFromId(UserEntity userEntityFromId) {
        this.userEntityFromId = userEntityFromId;
    }

    public String getRequestMessage() {
        return this.requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getUpdateDateTime() {
        return this.updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getFullAddress() {
        return this.fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getLatitute() {
        return this.latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongitute() {
        return this.longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public String getRequestTo() {
        return this.requestTo;
    }

    public void setRequestTo(String requestTo) {
        this.requestTo = requestTo;
    }
}
