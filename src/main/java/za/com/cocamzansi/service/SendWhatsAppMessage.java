package za.com.cocamzansi.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendWhatsAppMessage {
    public static final String ACCOUNT_SID = "ACXXXXXXXXXXXX";

    public static final String AUTH_TOKEN = "your_auth_token";

    public static void main(String[] args) {
        Twilio.init("ACXXXXXXXXXXXX", "your_auth_token");
        Message message = (Message)Message.creator(new PhoneNumber("whatsapp:+15005550006"), new PhoneNumber("whatsapp:+14155238886"), "Hello from your friendly neighborhood Java application!").create();
    }
}
