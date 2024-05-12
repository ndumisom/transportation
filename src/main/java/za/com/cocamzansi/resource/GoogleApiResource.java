package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.model.geolocationmodels.Coordinates;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;
import za.com.cocamzansi.service.AddressService;
import za.com.cocamzansi.service.GeolocationService;
import za.com.cocamzansi.service.LogoutService;
import za.com.cocamzansi.service.UserService;

@RestController
@RequestMapping({"/v1"})
public class GoogleApiResource {
    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    GeolocationService geolocationService;

    @Autowired
    LogoutService logoutService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = {"/geolocation"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Get User type", response = UserModel.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful reminder search", response = UserModel.class, responseContainer = "List"), @ApiResponse(code = 404, message = "Reminder does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public GeoLocation geolocation(HttpServletRequest requests, @RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude, @RequestParam(value = "destinationAddress", required = false) String destinationAddress) throws UnsupportedEncodingException {
        HttpSession checkUserSession = requests.getSession(false);
        if (checkUserSession == null) {
            System.out.println("Session is null");
            GeoLocation geoLocation1 = new GeoLocation();
            return geoLocation1;
        }
        Object user = checkUserSession.getAttribute("usersession");
        if (user == null) {
            System.out.println("user is not logged in");
            GeoLocation geoLocation1 = new GeoLocation();
            return geoLocation1;
        }
        System.out.println("user is logged in");
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(latitude);
        coordinates.setLng(longitude);
        GeoLocation geoLocation = this.geolocationService.getGeolocationByUserAndAddress(coordinates, destinationAddress);
        return geoLocation;
    }

    @RequestMapping(value = {"/currentaddress"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Get User type", response = UserModel.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful reminder search", response = UserModel.class, responseContainer = "List"), @ApiResponse(code = 404, message = "Reminder does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public GeoLocation geolocation(@ApiParam(name = "latitude", value = "latitude", required = true) @RequestParam String latitude, @ApiParam(name = "longitude", value = "longitude", required = true) @RequestParam String longitude) throws Exception {
        return this.geolocationService.currentAddress(latitude, longitude);
    }
}