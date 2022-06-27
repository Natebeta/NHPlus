package jobrunner;

import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Method that deletes every treatment that is older then 10 years
 */
public class DeleteTreatmentJob implements Job {

    /**
     * Gets the local date minus 10 years and delete older jobs than 10 years and deletes the treatment
     * @param jobExecutionContext
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LocalDate date = LocalDate.now().minusYears(10);
            TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
            int deleted = dao.deleteOlderThanDate(date);

            Logger.getAnonymousLogger().log(Level.INFO,
                    String.format("Deleted %s Treatments from before %s", deleted, date));

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().log(Level.WARNING,
                    String.format("Encountered an exception while running the %s Exception::%s",
                            DeleteTreatmentJob.class, e.getLocalizedMessage()));
        }
    }
}