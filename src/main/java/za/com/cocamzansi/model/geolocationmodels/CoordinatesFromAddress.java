package za.com.cocamzansi.model.geolocationmodels;

import java.util.ArrayList;

public class CoordinatesFromAddress {
    ArrayList< CoordinateAddress > results = new ArrayList < CoordinateAddress > ();
    private String status;

    public ArrayList<CoordinateAddress> getResults() {
        return results;
    }

    public void setResults(ArrayList<CoordinateAddress> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
