package za.com.cocamzansi.service;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.jobs.ResponseJob;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.service.EmailService;
import za.com.cocamzansi.service.ScheduleResponseService;

@Service
public class ScheduleResponseServiceImpl implements ScheduleResponseService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    EmailService emailService;

    @Autowired
    HttpServletRequest request;

    public void scheduleResponse(RequestModel requestModel) throws SchedulerException, IOException, MessagingException {
        System.out.println("***********************Schedule");
        HttpSession session = null;
        session = this.request.getSession(false);
        String userId = session.getAttribute("userId").toString();
        JobDataMap jobDataMap = new JobDataMap();
        JobDetail job = JobBuilder.newJob(ResponseJob.class).withIdentity("job2", "group2").withIdentity(requestModel.getRequestId().toString() + ":" + userId).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2").startNow().withSchedule((ScheduleBuilder)SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
        this.scheduler.scheduleJob(job, trigger);
    }

    public void scheduleResponseStop() throws Exception {
        String groupName = "group2";
        String jobName = "trigger2";
        JobKey jobKey = new JobKey(jobName, groupName);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        Trigger trigger = this.scheduler.getTrigger(triggerKey);
        if (trigger != null) {
            System.out.println("Trigger key name to stop " + triggerKey.getName());
            this.scheduler.unscheduleJob(triggerKey);
            System.out.println("The ScraperOnRequestJob having " + groupName + " has been stopped.");
        } else {
            System.out.println("No trigger could be found for " + triggerKey.getName());
        }
    }
}
