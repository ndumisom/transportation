package za.com.cocamzansi.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import za.com.cocamzansi.entity.AddressEntity;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;
import za.com.cocamzansi.repository.AddressEntityReposity;
import za.com.cocamzansi.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressEntityReposity addressEntityReposity;

    RestTemplate restTemplate = new RestTemplate();

    public ActionStatusType createUserAddress(AddressModel addressModel) throws UnsupportedEncodingException {
        if (addressModel == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.save.null.object");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setPostalcode(addressModel.getPostalcode());
        addressEntity.setStreetName(addressModel.getStreetName());
        String streetNumber = addressModel.getStreetNumber().toString();
        String streetNumberNumeric = streetNumber.replaceAll("[^0-9.]", "");
        Integer streetNum = Integer.valueOf(Integer.parseInt(streetNumberNumeric));
        addressEntity.setStreetNumber(streetNum);
        addressEntity.setSuburb(addressModel.getSuburb());
        addressEntity.setCity(addressModel.getCity());
        getGeolocation(addressEntity.getStreetNumber() + " " + addressEntity.getStreetName() + " " + addressEntity.getSuburb() + " " + addressEntity.getCity() + " " + addressEntity);
        addressEntity.setFullAddress("");
        if (addressEntity == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.save.null.object");
        ActionStatusType actionStatusType = new ActionStatusType(Boolean.valueOf(true), "Address.created.successful");
        if (this.addressEntityReposity.checkAddressExists(addressModel.getStreetNumber(), addressModel.getStreetName(), addressModel.getSuburb(), addressModel.getPostalcode()).size() < 1) {
            AddressEntity newCreatedEntity = (AddressEntity)this.addressEntityReposity.saveAndFlush(addressEntity);
            return new ActionStatusType(Boolean.valueOf(false), "Address.create.success");
        }
        return new ActionStatusType(Boolean.valueOf(false), "Address.already.exists");
    }

    public GeoLocation getGeolocation(String fullUserAddres) throws UnsupportedEncodingException {
        List<String> originAddress = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StringBuilder stringBuilder = new StringBuilder();
        String currentAddressTopass = "9 Caledon StTownsend Estate Cape Town 7460";
        String originalAddres = "4 Mispel Rd Bellville Park Cape Town 7530 South Africa";
        for (String originalAddress : originAddress)
            stringBuilder.append(originalAddres + "|");
        String addresses = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + fullUserAddres + "&destinations=" + currentAddressTopass + "&departure_time=now&key=AIzaSyCTiokuqarRi2kQlcWUGvLtCOkAez_NW_0";
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

    public ActionStatusType updateUserAddress(AddressModel addressModel) {
        if (addressModel.getAddressId() == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.update.address.id.not.provided");
        AddressEntity addressEntity = this.addressEntityReposity.findById(addressModel.getAddressId()).get();
        if (addressEntity.getPostalcode() == null || addressEntity.getStreetName() == null || addressEntity.getStreetNumber() == null || addressEntity.getSuburb() == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.update.none.existing.object");
        if (addressModel.getPostalcode() == null || addressModel.getStreetName() == null || addressModel.getStreetNumber() == null || addressModel.getSuburb() == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.update.with.null.value(s)");
        addressEntity.setPostalcode(addressModel.getPostalcode());
        addressEntity.setStreetName(addressModel.getStreetName());
        addressEntity.setStreetNumber(addressModel.getStreetNumber());
        addressEntity.setSuburb(addressModel.getSuburb());
        addressEntity.setCity(addressModel.getCity());
        if (this.addressEntityReposity.checkAddressExists(addressEntity.getStreetNumber(), addressEntity.getStreetName(), addressEntity.getSuburb(), addressEntity.getPostalcode()).size() < 1) {
            this.addressEntityReposity.saveAndFlush(addressEntity);
            return new ActionStatusType(Boolean.valueOf(true), "Address.updated.successful");
        }
        return new ActionStatusType(Boolean.valueOf(false), "Address.already.exists");
    }

    public List<AddressModel> getListOfAddresses() {
        List<AddressModel> addressModels = new ArrayList<>();
        List<AddressEntity> addressEntities = this.addressEntityReposity.findAll();
        for (AddressEntity addressEntity : addressEntities) {
            AddressModel addressModel = new AddressModel();
            addressModel.setAddressId(addressEntity.getAddressId());
            addressModel.setPostalcode(addressEntity.getPostalcode());
            addressModel.setSuburb(addressEntity.getSuburb());
            addressModel.setCity(addressEntity.getCity());
            addressModel.setStreetName(addressEntity.getStreetName());
            addressModel.setStreetNumber(addressEntity.getStreetNumber());
            addressModels.add(addressModel);
        }
        return addressModels;
    }
}
