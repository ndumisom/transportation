package za.com.cocamzansi.service;

import java.io.IOException;
import javax.mail.MessagingException;
import za.com.cocamzansi.model.EmailConfirmationModel;

public interface EmailConfirmService {
    EmailConfirmationModel getEmailConfirmationByTockenId(String paramString);

    EmailConfirmationModel createEmailConfirmation(EmailConfirmationModel paramEmailConfirmationModel) throws IOException, MessagingException;
}
