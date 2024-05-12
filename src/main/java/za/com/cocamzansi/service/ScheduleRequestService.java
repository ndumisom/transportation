package za.com.cocamzansi.service;

import org.quartz.SchedulerException;

public interface ScheduleRequestService {
    void schedule() throws SchedulerException;

    void stopSchedularJob() throws Exception;
}
