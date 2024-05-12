package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.EmailConfirmationModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.EmailConfirmService;

@RestController
@RequestMapping({"/v1"})
public class EmailConfirmationAddress {
    @Autowired
    EmailConfirmService emailConfirmService;

    @Autowired
    HttpServletResponse httpServletResponse;

    @RequestMapping(value = {"/emailconfirmation/{emailURL}"}, method = {RequestMethod.GET}, produces = {"text/html"})
    @ApiOperation(value = "Create request", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful creation of request", response = RequestModel.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public String getEmailConfirmationByTockenId(@ApiParam(name = "emailURL", value = "emailURL", required = true) @PathVariable String emailURL) throws UnsupportedEncodingException {
        try {
            EmailConfirmationModel emailConfirmationModel = this.emailConfirmService.getEmailConfirmationByTockenId(emailURL);
            this.httpServletResponse.sendRedirect("http://localhost/dreams/Login.html");
            return "Successful registered";
        } catch (Exception e) {
            e.printStackTrace();
            return "Not successful registered";
        }
    }
}
