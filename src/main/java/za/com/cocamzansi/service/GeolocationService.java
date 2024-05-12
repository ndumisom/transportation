package za.com.cocamzansi.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import za.com.cocamzansi.model.geolocationmodels.AddressCurrentLocation;
import za.com.cocamzansi.model.geolocationmodels.Coordinates;
import za.com.cocamzansi.model.geolocationmodels.CoordinatesFromAddress;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;

public interface GeolocationService {
    GeoLocation getGeolocation(Coordinates paramCoordinates, String paramString) throws UnsupportedEncodingException;

    AddressCurrentLocation currentCoordinates() throws UnsupportedEncodingException;

    GeoLocation currentAddress(String paramString1, String paramString2) throws UnsupportedEncodingException;

    GeoLocation getGeolocationByUserAndAddress(Coordinates paramCoordinates, String paramString) throws UnsupportedEncodingException;

    CoordinatesFromAddress coordinatesByAddress(String paramString) throws UnsupportedEncodingException;

    GeoLocation getGeolocationByRequestAddress(Coordinates paramCoordinates, String paramString) throws UnsupportedEncodingException;

    GeoLocation getGeolocationByRequest(Coordinates paramCoordinates, String paramString) throws UnsupportedEncodingException, ParseException;
}
