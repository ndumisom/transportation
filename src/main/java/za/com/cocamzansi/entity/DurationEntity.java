package za.com.cocamzansi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import za.com.cocamzansi.entity.RequestAuditEntity;
import za.com.cocamzansi.entity.UserEntity;

@Entity
@Table(name = "duration")
public class DurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "duration_id")
    private Integer durationId;

    @ManyToOne
    @JoinColumn(name = "request_audit_id")
    RequestAuditEntity requestAuditId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity userId;

    @Column(name = "status")
    private String status;

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

    public Integer getDurationId() {
        return this.durationId;
    }

    public void setDurationId(Integer durationId) {
        this.durationId = durationId;
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

    public UserEntity getUserId() {
        return this.userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public RequestAuditEntity getRequestAuditId() {
        return this.requestAuditId;
    }

    public void setRequestAuditId(RequestAuditEntity requestAuditId) {
        this.requestAuditId = requestAuditId;
    }
}
