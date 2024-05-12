package za.com.cocamzansi.model.geolocationmodels;

import java.util.ArrayList;

public class CurrentDeviceInformation {

    private float homeMobileCountryCode;
    private float homeMobileNetworkCode;
    private String radioType;
    private String carrier;
    private Boolean considerIp;
    ArrayList < Object > cellTowers = new ArrayList< Object >();
    ArrayList < Object > wifiAccessPoints = new ArrayList < Object > ();


    // Getter Methods

    public float getHomeMobileCountryCode() {
        return homeMobileCountryCode;
    }

    public float getHomeMobileNetworkCode() {
        return homeMobileNetworkCode;
    }

    public String getRadioType() {
        return radioType;
    }

    public String getCarrier() {
        return carrier;
    }

    // Setter Methods

    public void setHomeMobileCountryCode(float homeMobileCountryCode) {
        this.homeMobileCountryCode = homeMobileCountryCode;
    }

    public void setHomeMobileNetworkCode(float homeMobileNetworkCode) {
        this.homeMobileNetworkCode = homeMobileNetworkCode;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Boolean getConsiderIp() {
        return considerIp;
    }

    public void setConsiderIp(Boolean considerIp) {
        this.considerIp = considerIp;
    }
}
