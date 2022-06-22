package jobrunner;

import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DeleteTreatmentJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LocalDate date = LocalDate.now().minusYears(1);
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