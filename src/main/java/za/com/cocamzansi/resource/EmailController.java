package za.com.cocamzansi.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.model.exception.ExceptionResponseType;
import za.com.cocamzansi.service.EmailService;

@RestController
@RequestMapping({"/v1"})
public class EmailController {
    @Autowired
    EmailService emailService;

    @RequestMapping(value = {"/sendemail"}, method = {RequestMethod.POST}, consumes = {"application/json"}, produces = {"text/html"})
    @ApiOperation(value = "Email sent", response = ActionStatusType.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Successful email sent", response = ActionStatusType.class), @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponseType.class)})
    public String addUser(@ApiParam(name = "emailModel", value = "RequestEntity payload", required = true) @Valid @RequestBody EmailModel emailModel) throws IOException, MessagingException {
        return this.emailService.sendmail(emailModel);
    }
}
