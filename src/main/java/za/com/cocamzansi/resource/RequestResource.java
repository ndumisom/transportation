package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.EmailService;
import za.com.cocamzansi.service.RequestService;
import za.com.cocamzansi.service.ScheduleRequestService;
import za.com.cocamzansi.service.ScheduleResponseService;

@RestController
@RequestMapping({"/v1"})
public class RequestResource {
    @Autowired
    RequestService requestService;

    @Autowired
    ScheduleRequestService scheduleRequestService;

    @Autowired
    ScheduleResponseService scheduleResponseService;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = {"/request"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of request", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType addUser(@ApiParam(name = "requestModel", value = "RequestEntity payload", required = true) @Valid @RequestBody RequestModel requestModel) throws IOException, SchedulerException, MessagingException {
        ActionStatusType actionStatusType = this.requestService.createRequest(requestModel);
        this.scheduleRequestService.schedule();
        return actionStatusType;
    }

    @RequestMapping(value = {"/request/{userId}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of request", response = RequestModel.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<RequestModel> listOfRequests(@ApiParam(name = "userId", value = "userId", required = true) @PathVariable Integer userId) throws UnsupportedEncodingException {
        try {
            return this.requestService.listOfRequestsByUserId(userId);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/updateRequest"}, method = {RequestMethod.PUT}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create User Address", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of User", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType updateUserAddress(@ApiParam(name = "requestModel", value = "RequestEntity payload", required = true) @Valid @RequestBody RequestModel requestModel) throws SchedulerException, IOException, MessagingException {
        ActionStatusType actionStatusType = this.requestService.updateRequestByRequestId(requestModel);
        this.scheduleResponseService.scheduleResponse(requestModel);
        return actionStatusType;
    }
}
