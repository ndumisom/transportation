package za.com.cocamzansi.resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.com.cocamzansi.service.ScheduleRequestService;

@RestController
@RequestMapping({"/v1"})
public class ScheduleResource {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    ScheduleRequestService scheduleRequestService;

    @GetMapping({"/schedule"})
    public void schedule() throws SchedulerException {
        this.scheduleRequestService.schedule();
    }

    @GetMapping({"/stopschedule"})
    void stopSchedularJob() throws Exception {
        this.scheduleRequestService.stopSchedularJob();
    }
}
