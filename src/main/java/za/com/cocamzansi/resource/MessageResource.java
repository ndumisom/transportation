package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.UnsupportedEncodingException;
import javax.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.MessageModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.MessageService;

@RestController
@RequestMapping({"/v1"})
public class MessageResource {
    @Autowired
    MessageService messageService;

    @RequestMapping(value = {"/message"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Message sent successful", response = ActionStatusType.class), @ApiResponse(code = 400, message = "Mandatory field cannot be empty", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public ActionStatusType createMessage(@ApiParam(name = "requestModel", value = "RequestEntity payload", required = true) @Valid @RequestBody MessageModel messageModel) throws UnsupportedEncodingException, SchedulerException {
        ActionStatusType actionStatusType = this.messageService.createMessage(messageModel);
        return actionStatusType;
    }
}
