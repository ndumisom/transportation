package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusLoginType;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.LoginModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.AddressService;
import za.com.cocamzansi.service.LoginService;

@RestController
@RequestMapping({"/v1"})
public class LoginResource {
    @Autowired
    AddressService addressService;

    @Autowired
    LoginService loginService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "User Login", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of User", response = ActionStatusLoginType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusLoginType createLogin(@ApiParam(name = "LoginModel", value = "RequestEntity payload", required = true) @Valid @RequestBody LoginModel loginModel) {
        return this.loginService.login(loginModel);
    }
}
