package za.com.cocamzansi.model;

import java.math.BigDecimal;
import java.util.Date;
import za.com.cocamzansi.model.ServiceCategoryModel;

public class ServiceModel {
    private Integer serviceId;

    private String serviceName;

    private BigDecimal servicePrice;

    private Date createDateTime;

    private ServiceCategoryModel serviceCategoryModel;

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

    public ServiceCategoryModel getServiceCategoryModel() {
        return this.serviceCategoryModel;
    }

    public void setServiceCategoryModel(ServiceCategoryModel serviceCategoryModel) {
        this.serviceCategoryModel = serviceCategoryModel;
    }
}
