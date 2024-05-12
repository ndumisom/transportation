package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ServiceModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.ServicePriceService;

@RestController
@RequestMapping({"/v1"})
public class ServiceResource {
    @Autowired
    ServicePriceService servicePriceService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/services"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @ApiResponses({@ApiResponse(code = 200, message = "Successful User search", response = ServiceModel.class), @ApiResponse(code = 404, message = "User does not found", response = ExceptionResponseType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public List<ServiceModel> listOfServices() {
        return this.servicePriceService.listOfServices();
    }
}
