package za.com.cocamzansi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_category")
public class ServiceCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_category_id")
    private Integer serviceCategoryId;

    @Column(name = "service_category_name")
    private String serviceCategoryName;

    public Integer getServiceCategoryId() {
        return this.serviceCategoryId;
    }

    public void setServiceCategoryId(Integer serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName() {
        return this.serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }
}
