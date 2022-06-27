package jobrunner;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzController {
    public static void startTreatmentDeleter() {
        JobDetail job = JobBuilder.newJob(DeleteTreatmentJob.class).withIdentity("delete_treatment_job").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("delete_treatment_trigger").forJob(job)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 9)).startNow().build();

        try {
            Scheduler schedule = new StdSchedulerFactory().getScheduler();
            schedule.start();
            schedule.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
