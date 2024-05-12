package za.com.cocamzansi.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;

public interface AddressService {
    ActionStatusType createUserAddress(AddressModel paramAddressModel) throws UnsupportedEncodingException;

    ActionStatusType updateUserAddress(AddressModel paramAddressModel);

    List<AddressModel> getListOfAddresses();

    GeoLocation getGeolocation(String paramString) throws UnsupportedEncodingException;
}

