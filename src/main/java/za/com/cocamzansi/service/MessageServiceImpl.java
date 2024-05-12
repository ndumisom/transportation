package za.com.cocamzansi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.MessageEntity;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.MessageModel;
import za.com.cocamzansi.repository.MessageRepository;
import za.com.cocamzansi.service.MessageService;
import za.com.cocamzansi.util.localization.MessageByLocaleService;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private MessageByLocaleService messageSource;

    public ActionStatusType createMessage(MessageModel messageModel) {
        MessageEntity messageEntity = new MessageEntity();
        if (messageModel.getEmail() != null && !messageModel.getEmail().isEmpty()) {
            messageEntity.setEmail(messageModel.getEmail());
        } else {
            return new ActionStatusType(Boolean.valueOf(false), "Email address cannot be empty");
        }
        if (messageModel.getMessage() != null && !messageModel.getMessage().isEmpty()) {
            messageEntity.setMessage(messageModel.getMessage());
        } else {
            return new ActionStatusType(Boolean.valueOf(false), "Message body cannot be empty");
        }
        if (messageModel.getName() != null && !messageModel.getName().isEmpty()) {
            messageEntity.setName(messageModel.getName());
        } else {
            return new ActionStatusType(Boolean.valueOf(false), "Name cannot be empty");
        }
        if (messageModel.getPhonenumber() != null && !messageModel.getPhonenumber().isEmpty()) {
            messageEntity.setPhonenumber(messageModel.getPhonenumber());
        } else {
            return new ActionStatusType(Boolean.valueOf(false), "Phone number cannot be empty");
        }
        this.messageRepository.save(messageEntity);
        return new ActionStatusType(Boolean.valueOf(false), "Message sent successful");
    }
}
