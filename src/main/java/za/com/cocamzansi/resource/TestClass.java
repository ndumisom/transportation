package za.com.cocamzansi.resource;
import com.vonage.client.HttpConfig;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.SmsSubmissionResponseMessage;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import za.com.cocamzansi.service.SmsService;
import za.com.cocamzansi.service.SmsServiceImpl;

import javax.mail.MessagingException;
import java.io.IOException;

public class TestClass {
    @Autowired
    SmsService smsService;


    public static void main( String args []) throws IOException, MessagingException {

//        TestClass testClass = new TestClass();
//        testClass.getSmsService1();

        SmsServiceImpl smsService1 = new SmsServiceImpl();

//        smsService1.sendSMSByRequestId("");
//        HttpConfig httpConfig = HttpConfig.builder().baseUri("https://rest.nexmo.com").build();
//
//        VonageClient client = VonageClient.builder()
//                .apiKey("a35b4bc4")
//                .apiSecret("Vi8XtU0p05hmQQV0")
//                .httpConfig(httpConfig)
//                .build();
//
//
//
//        String messageText = "Hello from Vonage SMS API";
//        TextMessage message = new TextMessage("Vonage APIs", "27731230109", messageText);
//
//        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
//
//        for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
//            System.out.println(responseMessage);
//        }

    }

    public SmsService getSmsService() {
        return smsService;
    }

    public String getSmsService1() throws IOException, MessagingException {

//        smsService.sendSMSByRequestId("hdhdh");
        return "";
    }

    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
}
