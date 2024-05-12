package za.com.cocamzansi.service;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.model.RequestModel;

public interface EmailService {
     String sendmail(EmailModel paramEmailModel) throws AddressException, MessagingException, IOException;

     void sendEmailByRequestId(RequestModel paramRequestModel) throws IOException, MessagingException;
}
