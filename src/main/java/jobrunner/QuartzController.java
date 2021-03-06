package jobrunner;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Starts the job @DeleteTreatmentJob that deletes all treatments older than 10 years every day at 11:47 a.m.
 */
public class QuartzController {
    public static void startTreatmentDeleter() {
        JobDetail job = JobBuilder.newJob(DeleteTreatmentJob.class).withIdentity("delete_treatment_job").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("delete_treatment_trigger").forJob(job)
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(11, 47)).startNow().build();

        try {
            Scheduler schedule = new StdSchedulerFactory().getScheduler();
            schedule.start();
            schedule.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
