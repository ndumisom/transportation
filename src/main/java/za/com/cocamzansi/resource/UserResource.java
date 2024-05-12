package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.UserService;
import za.com.cocamzansi.util.localization.MessageByLocaleService;

@RestController
@RequestMapping({"/v1"})
public class UserResource {
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    HttpSessionEvent event;

    @Autowired
    private MessageByLocaleService localeService;

    @RequestMapping(value = {"/user"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create User", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of User", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType addUser(@ApiParam(name = "userModel", value = "RequestEntity payload", required = true) @Valid @RequestBody UserModel userModel) throws IOException, MessagingException {
        return this.userService.createUser(userModel);
    }

    @RequestMapping(value = {"/users/address"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiResponses({@ApiResponse(code = 200, message = "Successful User search", response = UserModel.class), @ApiResponse(code = 404, message = "User does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<UserModel> getUserByFullAddress(@RequestParam("latitude") String fullAddress) {
        return this.userService.getUserByFullAddress(fullAddress);
    }

    @RequestMapping(value = {"/users/{userId}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiResponses({@ApiResponse(code = 200, message = "Successful User search", response = UserModel.class), @ApiResponse(code = 404, message = "User does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public UserModel getUser(@ApiParam(name = "userId", value = "userId", required = true) @PathVariable Integer userId) {
        return this.userService.getUser(userId);
    }

    @RequestMapping(value = {"/user/{userId}"}, method = {RequestMethod.DELETE}, produces = {"application/json"})
    @ApiOperation(value = "Delete User", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful deletion of user", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType deleteUser(@ApiParam(name = "userId", value = "userId", required = true) @PathVariable Integer userId) {
        if (this.request.getSession().getAttribute("usersession") == null)
            return new ActionStatusType(Boolean.valueOf(false), "Login.please");
        return this.userService.delete(userId);
    }

    @RequestMapping(value = {"/users"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Get User Reminder", response = UserModel.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful reminder search", response = UserModel.class, responseContainer = "List"), @ApiResponse(code = 404, message = "Reminder does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<UserModel> getUsers(HttpServletRequest requests) throws Exception {
        HttpSession checkUserSession = requests.getSession(false);
        if (checkUserSession == null) {
            List<UserModel> userModels = new ArrayList<>();
            UserModel userModel = new UserModel();
            userModel.setFirstName("User not signed in");
            userModels.add(userModel);
            return userModels;
        }
        Object user = checkUserSession.getAttribute("usersession");
        if (user == null) {
            List<UserModel> userModels = new ArrayList<>();
            UserModel userModel = new UserModel();
            userModel.setFirstName("User not signed in");
            userModels.add(userModel);
            return userModels;
        }
        return this.userService.getUsers();
    }

    @RequestMapping(value = {"/usertype/{userTypeId}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Get User Reminder", response = UserModel.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful reminder search", response = UserModel.class, responseContainer = "List"), @ApiResponse(code = 404, message = "Reminder does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<UserModel> getAllByUserType(@ApiParam(name = "userTypeId", value = "userTypeId", required = true) @PathVariable Integer userTypeId) {
        return this.userService.getUserByType(userTypeId);
    }

    @RequestMapping(value = {"/useraddress/{userAddressId}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Get Users", response = UserModel.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful User search", response = UserModel.class, responseContainer = "List"), @ApiResponse(code = 404, message = "Reminder does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<UserModel> getAllByUserAddress(@ApiParam(name = "userAddressId", value = "userAddressId", required = true) @PathVariable Integer userAddressId) {
        return this.userService.getUserByAddress(userAddressId);
    }
}
