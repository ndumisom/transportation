package za.com.cocamzansi.service;

import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.MessageModel;

public interface MessageService {
    ActionStatusType createMessage(MessageModel paramMessageModel);
}
