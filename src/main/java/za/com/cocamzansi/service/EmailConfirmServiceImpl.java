package za.com.cocamzansi.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.EmailAddressEntity;
import za.com.cocamzansi.entity.EmailConfirmationEntity;
import za.com.cocamzansi.entity.LoginEntity;
import za.com.cocamzansi.entity.UserEntity;
import za.com.cocamzansi.model.EmailConfirmationModel;
import za.com.cocamzansi.model.EmailModel;
import za.com.cocamzansi.repository.EmailAddressRepository;
import za.com.cocamzansi.repository.EmailConfirmationRepository;
import za.com.cocamzansi.repository.LoginRepository;
import za.com.cocamzansi.repository.UserRepository;
import za.com.cocamzansi.service.EmailConfirmService;
import za.com.cocamzansi.service.EmailService;

@Service
public class EmailConfirmServiceImpl implements EmailConfirmService {
    @Autowired
    EmailConfirmationRepository emailConfirmationRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    EmailAddressRepository emailAddressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRepository loginRepository;

    public EmailConfirmationModel getEmailConfirmationByTockenId(String token) {
        EmailConfirmationModel emailConfirmationModel = new EmailConfirmationModel();
        EmailConfirmationEntity emailConfirmationEntity = this.emailConfirmationRepository.getEmailConfirmationByConfirmationToken(token);
        if (emailConfirmationEntity != null) {
            emailConfirmationEntity.setEnabled(true);
            List<EmailAddressEntity> emailAddressEntities = this.emailAddressRepository.checkEmailAddressExists(emailConfirmationEntity.getEmail());
            EmailAddressEntity emailAddressEntity = emailAddressEntities.get(0);
            List<UserEntity> userEntities = this.userRepository.findAllByUserEmailAddressEntity(emailAddressEntity);
            emailConfirmationEntity.setUserEntity(userEntities.get(0));
            EmailConfirmationEntity emailConfirmationEntityNew = (EmailConfirmationEntity)this.emailConfirmationRepository.save(emailConfirmationEntity);
            List<LoginEntity> loginEntities = this.loginRepository.findByUserId(userEntities.get(0));
            LoginEntity loginEntity = loginEntities.get(0);
            loginEntity.setEnabled(true);
            this.loginRepository.save(loginEntity);
            emailConfirmationModel.setConfirmationToken(emailConfirmationEntity.getConfirmationToken());
            emailConfirmationModel.setConfirmemailId(emailConfirmationEntity.getConfirmemailId());
            emailConfirmationModel.setEmail(emailConfirmationEntity.getEmail());
            emailConfirmationModel.setEnabled(emailConfirmationEntity.isEnabled());
        }
        return emailConfirmationModel;
    }

    public EmailConfirmationModel createEmailConfirmation(EmailConfirmationModel emailConfirmationModel) throws IOException, MessagingException {
        EmailConfirmationEntity emailConfirmationEntity = new EmailConfirmationEntity();
        emailConfirmationEntity.setEmail(emailConfirmationModel.getEmail());
        emailConfirmationEntity.setConfirmationToken(UUID.randomUUID().toString());
        List<EmailAddressEntity> emailAddressEntities = this.emailAddressRepository.checkEmailAddressExists(emailConfirmationModel.getEmail());
        EmailAddressEntity emailAddressEntity = emailAddressEntities.get(0);
        List<UserEntity> userEntities = this.userRepository.findAllByUserEmailAddressEntity(emailAddressEntity);
        emailConfirmationEntity.setUserEntity(userEntities.get(0));
        EmailConfirmationEntity emailConfirmationEntityNew = (EmailConfirmationEntity)this.emailConfirmationRepository.saveAndFlush(emailConfirmationEntity);
        emailConfirmationModel.setConfirmemailId(emailConfirmationEntityNew.getConfirmemailId());
        emailConfirmationModel.setEnabled(false);
        emailConfirmationModel.setConfirmationToken(emailConfirmationEntityNew.getConfirmationToken());
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailAddressTo(emailConfirmationEntityNew.getEmail());
        emailModel.setEmailSubject("Please confirm your email address");
        emailModel.setEmailBody("Hello,\n\nPlease follow this link to verify your email address. \n\nhttp://localhost:8080/v1/emailconfirmation/" + emailConfirmationEntityNew.getConfirmationToken() + "\n\nIf you did't ask to verify this email address, you can ignore this email.\n\nThanks,\n\nlocalhost/dreams\n\n021 592 8010\n\n073 123 0109\n\n084 377 4028\n\ninfo@localhost/dreams");
        this.emailService.sendmail(emailModel);
        return emailConfirmationModel;
    }
}