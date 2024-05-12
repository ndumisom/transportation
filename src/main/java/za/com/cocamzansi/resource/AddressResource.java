package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.AddressService;

@RestController
@RequestMapping({"/v1"})
public class AddressResource {
    @Autowired
    AddressService addressService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/useraddress"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create User Address", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of User", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType createUserAddress(@ApiParam(name = "addressModel", value = "RequestEntity payload", required = true) @Valid @RequestBody AddressModel addressModel) throws UnsupportedEncodingException {
        if (this.request.getSession().getAttribute("usersession") == null)
            return new ActionStatusType(Boolean.valueOf(false), "Login.please");
        return this.addressService.createUserAddress(addressModel);
    }

    @RequestMapping(value = {"/updateuseraddress"}, method = {RequestMethod.PUT}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create User Address", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of User", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType updateUserAddress(@ApiParam(name = "addressModel", value = "RequestEntity payload", required = true) @Valid @RequestBody AddressModel addressModel) {
        return this.addressService.updateUserAddress(addressModel);
    }
}
