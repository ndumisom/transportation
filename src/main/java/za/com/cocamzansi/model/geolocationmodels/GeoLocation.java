package za.com.cocamzansi.model.geolocationmodels;

import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class GeoLocation {

    ArrayList<String> origin_addresses = new ArrayList < String > ();
    List<UserModel> userModels;
    List<RequestModel> requestModels;
    ArrayList < String > destination_addresses = new ArrayList < String > ();
    ArrayList < RowElementsDistanceDuration > rows = new ArrayList < RowElementsDistanceDuration > ();
    private String status;
    public ArrayList<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(ArrayList<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public ArrayList<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(ArrayList<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public ArrayList<RowElementsDistanceDuration> getRows() {
        return rows;
    }

    public void setRows(ArrayList<RowElementsDistanceDuration> rows) {
        this.rows = rows;
    }

// Getter Methods

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }


    public List<RequestModel> getRequestModels() {
        return requestModels;
    }

    public void setRequestModels(List<RequestModel> requestModels) {
        this.requestModels = requestModels;
    }
}
