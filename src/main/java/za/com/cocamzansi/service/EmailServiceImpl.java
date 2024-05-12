package za.com.cocamzansi.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.repository.RequestRepository;
import za.com.cocamzansi.service.EmailService;
import za.com.cocamzansi.service.RequestService;
import za.com.cocamzansi.service.ScheduleRequestService;
import za.com.cocamzansi.service.ServicePriceService;
import za.com.cocamzansi.service.SmsSenderService;
import za.com.cocamzansi.service.UserService;
import za.com.cocamzansi.util.localization.MessageByLocaleService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Autowired
    ServicePriceService servicePriceService;

    @Autowired
    ScheduleRequestService scheduleRequestService;

    @Autowired
    MessageByLocaleService messageByLocaleService;

    @Autowired
    EmailService emailService;

    @Autowired
    RequestService requestService;

    @Autowired
    SmsSenderService smsSenderService;

    @Async
    public String sendmail(EmailModel emailModel) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ayabonganic@gmail.com", "ayabonga@1805");
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom((Address)new InternetAddress("ayabonganic@gmail.com", false));
        mimeMessage.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(emailModel.getEmailAddressTo()));
        mimeMessage.setSubject(emailModel.getEmailSubject());
        mimeMessage.setContent(emailModel.getEmailBody(), "text/html");
        mimeMessage.setText("Hi there, this is my first message sent with JavaMail");
        mimeMessage.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailModel.getEmailBody(), "text/html");
        messageBodyPart.setText(emailModel.getEmailBody());
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart((BodyPart)messageBodyPart);
        mimeMessage.setContent((Multipart)mimeMultipart);
        Transport.send((Message)mimeMessage);
        return "Email sent successfully";
    }

    public void sendEmailByRequestId(RequestModel requestModel) throws IOException, MessagingException {
        System.out.println(requestModel.getRequestId());
        RequestEntity requestEntity = (RequestEntity)this.requestRepository.getOne(requestModel.getRequestId());
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailAddressTo(requestEntity.getUserEntityFromId().getEmailAddressEntity().getEmailAddress());
        emailModel.setEmailSubject(this.messageByLocaleService.getMessage("Response# : " + requestEntity.getRequestId()));
        emailModel.setEmailBody(this.messageByLocaleService.getMessage("Hi cocamzansi user your request response has been sent# : " + requestEntity.getRequestId()));
        if (this.emailService.sendmail(emailModel).equalsIgnoreCase("Email sent successfully")) {
            System.out.println("email sent to: " + requestEntity.getUserEntityFromId().getEmailAddressEntity().getEmailAddress());
            this.smsSenderService.sendSMS("2", "5");
        }
    }
}
