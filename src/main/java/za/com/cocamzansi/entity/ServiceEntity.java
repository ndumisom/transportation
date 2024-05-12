package za.com.cocamzansi.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import za.com.cocamzansi.entity.ServiceCategoryEntity;

@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_price")
    private BigDecimal servicePrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;

    @ManyToOne
    @JoinColumn(name = "service_category_id")
    private ServiceCategoryEntity serviceCategoryEntity;

    public Integer getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getServicePrice() {
        return this.servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Date getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public ServiceCategoryEntity getServiceCategoryEntity() {
        return this.serviceCategoryEntity;
    }

    public void setServiceCategoryEntity(ServiceCategoryEntity serviceCategoryEntity) {
        this.serviceCategoryEntity = serviceCategoryEntity;
    }
}
