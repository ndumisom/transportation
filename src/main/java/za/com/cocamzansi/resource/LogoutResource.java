package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.AddressService;
import za.com.cocamzansi.service.LogoutService;

@RestController
@RequestMapping({"/v1"})
public class LogoutResource {
    @Autowired
    AddressService addressService;

    @Autowired
    LogoutService logoutService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/logout"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiResponses({@ApiResponse(code = 404, message = "User does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType createLogout() {
        return this.logoutService.logout();
    }
}
