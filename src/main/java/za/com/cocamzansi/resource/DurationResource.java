package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.DurationModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.DurationService;

@RestController
@RequestMapping({"/v1"})
public class DurationResource {
    @Autowired
    DurationService durationService;

    @RequestMapping(value = {"/duration/{userId}/{requestId}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of request", response = RequestModel.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public DurationModel getEmailConfirmationByTockenId(@ApiParam(name = "userId", value = "userId", required = true) @PathVariable Integer userId, @ApiParam(name = "requestId", value = "requestId", required = true) @PathVariable Integer requestId) throws UnsupportedEncodingException {
        try {
            return this.durationService.findByRequestIdAndUserId(userId, requestId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/duration/{userId}"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of request", response = RequestModel.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<DurationModel> getUserHistory(@ApiParam(name = "userId", value = "userId", required = true) @PathVariable Integer userId) throws UnsupportedEncodingException {
        try {
            return this.durationService.findRequestByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
