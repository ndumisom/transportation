package za.com.cocamzansi.service;

import java.io.IOException;
import javax.mail.MessagingException;
import org.quartz.SchedulerException;
import za.com.cocamzansi.model.RequestModel;

public interface ScheduleResponseService {
    void scheduleResponse(RequestModel paramRequestModel) throws SchedulerException, IOException, MessagingException;

    void scheduleResponseStop() throws Exception;
}
