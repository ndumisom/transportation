package za.com.cocamzansi.service;

import org.quartz.JobBuilder;
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
import za.com.cocamzansi.jobs.RequestJob;
import za.com.cocamzansi.service.ScheduleRequestService;

@Service
public class ScheduleRequestServiceImpl implements ScheduleRequestService {
    @Autowired
    private Scheduler scheduler;

    public void schedule() throws SchedulerException {
        System.out.println("***********************Schedule");
        JobDetail job = JobBuilder.newJob(RequestJob.class).withIdentity("job1", "group1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().withSchedule((ScheduleBuilder)SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
        this.scheduler.scheduleJob(job, trigger);
    }

    public void stopSchedularJob() throws Exception {
        String groupName = "group1";
        String jobName = "trigger1";
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
