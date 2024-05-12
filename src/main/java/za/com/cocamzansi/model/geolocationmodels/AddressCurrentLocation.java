package za.com.cocamzansi.model.geolocationmodels;

public class AddressCurrentLocation {

    Location location;
    private float accuracy;


    // Getter Methods

    public Location getLocation() {
        return location;
    }

    public float getAccuracy() {
        return accuracy;
    }

    // Setter Methods

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
