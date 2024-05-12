package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.ReloadService;

@RestController
@RequestMapping({"/v1"})
public class ReloadResource {
    @Autowired
    ReloadService reloadService;

    @RequestMapping(value = {"/reload"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of request", response = RequestModel.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<RequestModel> listOfRequests() {
        try {
            return this.reloadService.listOfRequests();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
