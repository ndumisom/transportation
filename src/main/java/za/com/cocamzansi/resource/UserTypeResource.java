package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.UserTypeModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.UserTypeService;

@RestController
@RequestMapping({"/v1"})
public class UserTypeResource {
    @Autowired
    UserTypeService userTypeService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/usertypes"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Get User type", response = UserModel.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful reminder search", response = UserModel.class, responseContainer = "List"), @ApiResponse(code = 404, message = "Reminder does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<UserTypeModel> getAllByUserType() {
        return this.userTypeService.getUserByType();
    }
}
