package za.com.cocamzansi.model.geolocationmodels;

public class DistanceDuration {
        Distance distance;
        Duration duration;
        DurationInTraffic duration_in_traffic;
        private String status;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public DurationInTraffic getDuration_in_traffic() {
        return duration_in_traffic;
    }

    public void setDuration_in_traffic(DurationInTraffic duration_in_traffic) {
        this.duration_in_traffic = duration_in_traffic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
