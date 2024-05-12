package za.com.cocamzansi.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.service.SmsSenderService;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    public static final String ACCOUNT_SID = "ACd00e8155ec9749304ed58630a59629f1";

    public static final String AUTH_TOKEN = "e76021ed98ecfd062b026a6a179af540";

    public void sendSMS(String from, String to) {
        Twilio.init("ACd00e8155ec9749304ed58630a59629f1", "e76021ed98ecfd062b026a6a179af540");
        Message message = (Message)Message.creator(new PhoneNumber("whatsapp:+27828430060"), new PhoneNumber("whatsapp:+14155238886"), "Hi there ,this a message from cocamzansi whatapp group").create();
        System.out.println(message.getSid());
    }
}
