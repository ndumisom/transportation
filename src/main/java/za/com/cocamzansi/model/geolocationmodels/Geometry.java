package za.com.cocamzansi.model.geolocationmodels;

public class Geometry {

    Bounds Bounds;
    Location location;
    private String locationType;
    Viewport Viewport;

    public za.com.cocamzansi.model.geolocationmodels.Bounds getBounds() {
        return Bounds;
    }

    public void setBounds(za.com.cocamzansi.model.geolocationmodels.Bounds bounds) {
        Bounds = bounds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public za.com.cocamzansi.model.geolocationmodels.Viewport getViewport() {
        return Viewport;
    }

    public void setViewport(za.com.cocamzansi.model.geolocationmodels.Viewport viewport) {
        Viewport = viewport;
    }
}
