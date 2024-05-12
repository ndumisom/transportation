package za.com.cocamzansi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import org.quartz.SchedulerException;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.RequestModel;

import javax.mail.MessagingException;

public interface RequestService {
    ActionStatusType createRequest(RequestModel paramRequestModel) throws IOException, MessagingException;

    List<RequestModel> listOfRequestsByUserId(Integer paramInteger) throws ParseException, UnsupportedEncodingException;

    ActionStatusType updateRequestByRequestId(RequestModel paramRequestModel) throws SchedulerException;

    List<RequestModel> listOfRequests() throws ParseException, UnsupportedEncodingException;

    List<RequestModel> listOfRequestsByStatus() throws UnsupportedEncodingException;
}
