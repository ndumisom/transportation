package za.com.cocamzansi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "telephone")
public class TelephoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "telephone_id")
    private Integer telephoneId;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    public Integer getTelephoneId() {
        return this.telephoneId;
    }

    public void setTelephoneId(Integer telephoneId) {
        this.telephoneId = telephoneId;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
