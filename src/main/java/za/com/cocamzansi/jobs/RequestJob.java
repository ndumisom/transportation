package za.com.cocamzansi.jobs;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.repository.RequestRepository;
import za.com.cocamzansi.service.*;
import za.com.cocamzansi.util.localization.MessageByLocaleService;


import javax.mail.MessagingException;
import java.util.List;


/**
 *
 * @author Paulo Henrique
 */
public class RequestJob implements Job {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ScheduleRequestService scheduleRequestService;

    @Autowired
    MessageByLocaleService messageByLocaleService;

    @Autowired
    UserService userService;

    @Autowired
    SmsSenderService smsSenderService;

    @Autowired
    SmsService smsService;

    public RequestJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            System.err.println("Sample Job Executing");
            List<RequestEntity> requestEntityList = requestRepository.processRequest();
            if (requestEntityList.size() > 0) {
                RequestEntity requestEntity = requestEntityList.get(0);
                    EmailModel emailModel = new EmailModel();
                    UserModel userModel = userService.getUser(requestEntity.getUserEntityFromId().getUserId());
                    emailModel.setEmailAddressTo(requestEntity.getUserEntityFromId().getEmailAddressEntity().getEmailAddress());
                    emailModel.setEmailSubject(messageByLocaleService.getMessage("Coca Mzansi Request  # : " + requestEntity.getRequestId()));

                    String emailbody = "Dear \t"+userModel.getFirstName()+" \n" +
                            "\n" +
                            "Your request #"+requestEntity.getRequestId()+" has been sent, a reply to your request will be attended soon.\n" +
                            "\n";
                    emailModel.setEmailBody(messageByLocaleService.getMessage(emailbody));
                    scheduleRequestService.stopSchedularJob();
                    if (emailService.sendmail(emailModel).equalsIgnoreCase("Email sent successfully")){
                        scheduleRequestService.stopSchedularJob();
                        smsSenderService.sendSMS("8","7");
                        RequestModel requestModel = new RequestModel();


                        this.smsService.sendSMSByRequestId(requestModel);
                    //requestEntity.setStatus(1);
                    //requestRepository.saveAndFlush(requestEntity);
                    }

            }

            System.out.println("Find size for 0: " + requestRepository.processRequest().size());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
