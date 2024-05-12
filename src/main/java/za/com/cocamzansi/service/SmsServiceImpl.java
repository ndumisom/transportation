package za.com.cocamzansi.service;

import com.vonage.client.HttpConfig;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.SmsSubmissionResponseMessage;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.repository.UserRepository;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class SmsServiceImpl  implements SmsService{
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Override
    public void sendSMSByRequestId(RequestModel requestModel) throws IOException, MessagingException {

        HttpConfig httpConfig = HttpConfig.builder().baseUri("https://rest.nexmo.com").build();

        VonageClient client = VonageClient.builder()
                .apiKey("a35b4bc4")
                .apiSecret("Vi8XtU0p05hmQQV0")
                .httpConfig(httpConfig)
                .build();

//        UserModel userModel = userService.getUser(requestModel.getUserModelFromId().getUserId());


        String messageText = "Hello Amos,There is a new request on Coca Mzansi";
        TextMessage message = new TextMessage(requestModel.getUserToPhoneNumber(), "+27 72 842 5987", messageText);

        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
            System.out.println(responseMessage);
        }
    }
}
