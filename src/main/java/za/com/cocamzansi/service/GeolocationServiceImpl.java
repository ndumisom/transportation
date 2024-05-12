package za.com.cocamzansi.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.geolocationmodels.AddressCurrentLocation;
import za.com.cocamzansi.model.geolocationmodels.Coordinates;
import za.com.cocamzansi.model.geolocationmodels.CoordinatesFromAddress;
import za.com.cocamzansi.model.geolocationmodels.CurrentDeviceInformation;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;
import za.com.cocamzansi.repository.AddressEntityReposity;
import za.com.cocamzansi.service.AddressService;
import za.com.cocamzansi.service.GeolocationService;
import za.com.cocamzansi.service.RequestService;
import za.com.cocamzansi.service.UserService;

@Service
public class GeolocationServiceImpl implements GeolocationService {
    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressEntityReposity addressEntityReposity;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    RequestService requestService;

    RestTemplate restTemplate = new RestTemplate();

    public GeoLocation getGeolocation(Coordinates coordinates, String destinationAddress) throws UnsupportedEncodingException {
        if (this.request == null)
            System.out.println("request is null");
        HttpSession checkUserSession = this.request.getSession(false);
        if (checkUserSession == null)
            System.out.println("checkUserSession is null");
        String currentAddress = checkUserSession.getAttribute("currentaddress").toString();
        String currentAddressTopass = null;
        List<AddressModel> addressModels = this.addressService.getListOfAddresses();
        List<String> originAddress = new ArrayList<>();
        for (AddressModel addressModel : addressModels) {
            String buildStringAddress = addressModel.getStreetNumber() + " " + addressModel.getStreetName() + " " + addressModel.getSuburb() + " " + addressModel.getCity() + " " + addressModel.getPostalcode();
            originAddress.add(buildStringAddress);
        }
        if (destinationAddress != null && destinationAddress.trim().length() > 1) {
            currentAddressTopass = destinationAddress;
        } else {
            currentAddressTopass = currentAddress;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StringBuilder stringBuilder = new StringBuilder();
        for (String originalAddress : originAddress)
            stringBuilder.append(originalAddress + "|");
        String addresses = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + stringBuilder + "&destinations=" + currentAddressTopass + "&departure_time=now&key=AIzaSyCTiokuqarRi2kQlcWUGvLtCOkAez_NW_0";
        String addressesUrl = URLDecoder.decode(addresses, StandardCharsets.UTF_8.name());
        try {
            HttpEntity<String> entity = new HttpEntity((MultiValueMap)headers);
            ResponseEntity<GeoLocation> result = this.restTemplate.exchange(addressesUrl, HttpMethod.GET, entity, GeoLocation.class, new Object[0]);
            GeoLocation geoLocation = (GeoLocation)result.getBody();
            return geoLocation;
        } catch (Exception e) {
            return null;
        }
    }

    public AddressCurrentLocation currentCoordinates() throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StringBuilder stringBuilder = new StringBuilder();
        String addresses = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyCTiokuqarRi2kQlcWUGvLtCOkAez_NW_0";
        String addressesUrl = URLDecoder.decode(addresses, StandardCharsets.UTF_8.name());
        try {
            CurrentDeviceInformation currentDeviceInformation = new CurrentDeviceInformation();
            currentDeviceInformation.setRadioType("gsm");
            currentDeviceInformation.setHomeMobileCountryCode(27.0F);
            currentDeviceInformation.setHomeMobileNetworkCode(655.0F);
            currentDeviceInformation.setConsiderIp(Boolean.valueOf(true));
            HttpEntity<Object> entity = new HttpEntity(currentDeviceInformation, (MultiValueMap)headers);
            ResponseEntity<AddressCurrentLocation> result = this.restTemplate.exchange(addressesUrl, HttpMethod.POST, entity, AddressCurrentLocation.class, new Object[0]);
            AddressCurrentLocation geoLocation = (AddressCurrentLocation)result.getBody();
            return geoLocation;
        } catch (Exception e) {
            return null;
        }
    }

    public GeoLocation currentAddress(String latitude, String longitude) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String addresses = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=-33.9147785,18.5432328%7C-33.9816289%2C18.6351608&destinations=" + latitude + "," + longitude + "&key=AIzaSyCTiokuqarRi2kQlcWUGvLtCOkAez_NW_0";
        String addressesUrl = URLDecoder.decode(addresses, StandardCharsets.UTF_8.name());
        try {
            HttpEntity<String> entity = new HttpEntity((MultiValueMap)headers);
            ResponseEntity<GeoLocation> result = this.restTemplate.exchange(addressesUrl, HttpMethod.GET, entity, GeoLocation.class, new Object[0]);
            GeoLocation geoLocation = (GeoLocation)result.getBody();
            HttpSession session = null;
            session = this.request.getSession(true);
            session.setAttribute("currentaddress", geoLocation.getDestination_addresses().get(0));
            session.setAttribute("latitudeSession", latitude);
            session.setAttribute("longtuteSession", longitude);
            return geoLocation;
        } catch (Exception e) {
            return null;
        }
    }

    public GeoLocation getGeolocationByUserAndAddress(Coordinates coordinates, String destinationAddress) throws UnsupportedEncodingException {
        try {
            List<UserModel> userModels = this.userService.getUsers();
            GeoLocation geoLocations = getGeolocation(coordinates, destinationAddress);
            geoLocations.setUserModels(userModels);
            return geoLocations;
        } catch (Exception e) {
            return null;
        }
    }

    public CoordinatesFromAddress coordinatesByAddress(String fullAddress) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String addresses = "https://maps.googleapis.com/maps/api/geocode/json?address=" + fullAddress + "&key=AIzaSyCTiokuqarRi2kQlcWUGvLtCOkAez_NW_0";
        String addressesUrl = URLDecoder.decode(addresses, StandardCharsets.UTF_8.name());
        try {
            HttpEntity<String> entity = new HttpEntity((MultiValueMap)headers);
            ResponseEntity<CoordinatesFromAddress> result = this.restTemplate.exchange(addressesUrl, HttpMethod.GET, entity, CoordinatesFromAddress.class, new Object[0]);
            CoordinatesFromAddress coordinatesFromAddress = (CoordinatesFromAddress)result.getBody();
            return coordinatesFromAddress;
        } catch (Exception e) {
            return null;
        }
    }

    public GeoLocation getGeolocationByRequestAddress(Coordinates coordinates, String destinationAddress) throws UnsupportedEncodingException {
        try {
            List<RequestModel> requestModels = this.requestService.listOfRequestsByStatus();
            GeoLocation geoLocations = getGeolocationByRequest(coordinates, destinationAddress);
            geoLocations.setRequestModels(requestModels);
            return geoLocations;
        } catch (Exception e) {
            return null;
        }
    }

    public GeoLocation getGeolocationByRequest(Coordinates coordinates, String destinationAddress) throws UnsupportedEncodingException, ParseException {
        if (this.request == null)
            System.out.println("request is null");
        HttpSession checkUserSession = this.request.getSession(false);
        if (checkUserSession == null)
            System.out.println("checkUserSession is null");
        String currentAddress = checkUserSession.getAttribute("currentaddress").toString();
        String currentAddressTopass = null;
        List<RequestModel> requestModels = this.requestService.listOfRequests();
        List<String> originAddress = new ArrayList<>();
        for (RequestModel requestModel : requestModels) {
            String buildStringAddress = requestModel.getFullAddress();
            originAddress.add(buildStringAddress);
        }
        if (destinationAddress != null && destinationAddress.trim().length() > 1) {
            currentAddressTopass = destinationAddress;
        } else {
            currentAddressTopass = currentAddress;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StringBuilder stringBuilder = new StringBuilder();
        for (String originalAddress : originAddress)
            stringBuilder.append(originalAddress + "|");
        String addresses = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + stringBuilder + "&destinations=" + currentAddressTopass + "&departure_time=now&key=AIzaSyCTiokuqarRi2kQlcWUGvLtCOkAez_NW_0";
        String addressesUrl = URLDecoder.decode(addresses, StandardCharsets.UTF_8.name());
        try {
            HttpEntity<String> entity = new HttpEntity((MultiValueMap)headers);
            ResponseEntity<GeoLocation> result = this.restTemplate.exchange(addressesUrl, HttpMethod.GET, entity, GeoLocation.class, new Object[0]);
            GeoLocation geoLocation = (GeoLocation)result.getBody();
            return geoLocation;
        } catch (Exception e) {
            return null;
        }
    }
}
