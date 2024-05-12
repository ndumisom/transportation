package za.com.cocamzansi.jobs;

import com.sun.mail.util.MailConnectException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.repository.RequestRepository;
import za.com.cocamzansi.service.*;
import za.com.cocamzansi.util.localization.MessageByLocaleService;

import javax.mail.MessagingException;
import java.util.List;

public class SendEmailRegisterUserJob implements Job {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ScheduleRequestService scheduleRequestService;

    @Autowired
    ScheduleResponseService scheduleResponseService;

    @Autowired
    MessageByLocaleService messageByLocaleService;

    @Autowired
    UserService userService;

    @Autowired
    SmsSenderService smsSenderService;

    Integer requestId;

    public SendEmailRegisterUserJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            scheduleResponseService.scheduleResponseStop();
            System.err.println("Response Job Executing");

            List<RequestEntity> requestEntityList = requestRepository.processRequestAccepted();

            if(requestEntityList.size()>0) {
                String requestIdAndUserId = context.getJobDetail().getKey().getName();
                System.out.println(requestIdAndUserId);

                String requestAndUser[] = requestIdAndUserId.split(":");

                Integer requestId = Integer.parseInt(requestAndUser[0]);
                Integer userId = Integer.parseInt(requestAndUser[1]);

                for(RequestEntity requestEntity : requestEntityList) {
                    if(requestId.equals(requestEntity.getRequestId())) {
                        EmailModel emailModel = new EmailModel();
                        UserModel userModel = userService.getUser(userId);



                        String emailbody = "Your request has been accepted by "+userModel.getFirstName()+"\t"+userModel.getLastName()+".\n Please be at the location within an hour to welcome our agent to help you with your request.";
                        emailModel.setEmailAddressTo(requestEntity.getUserEntityFromId().getEmailAddressEntity().getEmailAddress());
                        emailModel.setEmailSubject(messageByLocaleService.getMessage("Response# : " + requestEntity.getRequestId()));
                        emailModel.setEmailBody(messageByLocaleService.getMessage(emailbody));

                        if (emailService.sendmail(emailModel).equalsIgnoreCase("Email sent successfully")) {
                            System.out.println("email sent to: " + requestEntity.getUserEntityFromId().getEmailAddressEntity().getEmailAddress());
                            smsSenderService.sendSMS("4","6");
                        }
                    }
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
}