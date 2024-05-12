package za.com.cocamzansi.service;

import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.model.RequestModel;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

public interface SmsService {
    void sendSMSByRequestId(RequestModel requestModel) throws IOException, MessagingException;
}
