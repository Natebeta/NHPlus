package model;

import datastorage.DAOFactory;
import utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The treatments for the patients
 */
public class Treatment {
    private long tid;
    private long pid;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;
    private boolean lockStatus;
    private long cid;

    /**
     * constructs a treatment from the given params.
     * @param pid
     * @param date
     * @param begin
     * @param end
     * @param description
     * @param remarks
     * @param cid
     */
    public Treatment(long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, boolean lockStatus, long cid) {
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.lockStatus = lockStatus;
        this.cid = cid;
    }

    /**
     * constructs a treatment from the given params.
     * @param tid
     * @param pid
     * @param date
     * @param begin
     * @param end
     * @param description
     * @param remarks
     * @param cid
     */
    public Treatment(long tid, long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, boolean lockStatus, long cid) {
        this.tid = tid;
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.lockStatus = lockStatus;
        this.cid = cid;
    }

    /**
     * @return tid
     */
    public long getTid() {
        return tid;
    }

    /**
     * @return
     */
    public long getPid() {
        return this.pid;
    }

    /**
     * @return
     */
    public String getDate() {
        return date.toString();
    }

    /**
     * @return
     */
    public String getBegin() {
        return begin.toString();
    }

    /**
     * @return
     */
    public String getEnd() {
        return end.toString();
    }

    /**
     * @param s_date new date
     */
    public void setDate(String s_date) {
        LocalDate date = DateConverter.convertStringToLocalDate(s_date);
        this.date = date;
    }

    /**
     * @param begin new begin
     */
    public void setBegin(String begin) {
        LocalTime time = DateConverter.convertStringToLocalTime(begin);
        this.begin = time;
    }

    /**
     * @param end new end
     */
    public void setEnd(String end) {
        LocalTime time = DateConverter.convertStringToLocalTime(end);
        this.end = time;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return
     */
    public Caregiver getCaregiver() throws SQLException {
        return DAOFactory.getDAOFactory().createCaregiverDAO().read(cid);
    }

    /**
     * @param caregiver new caregiver
     */
    public void setCaregiver(Caregiver caregiver) {
        this.cid = caregiver.getCid();
    }

    public long getCid() {
        return cid;
    }

    public String getCidSurname() throws SQLException {
        return DAOFactory.getDAOFactory().createCaregiverDAO().read(cid).getSurname();
    }

    public String getPidSurname() throws SQLException {
        return DAOFactory.getDAOFactory().createPatientDAO().read(pid).getSurname();
    }
    /**
     * @return string-representation of the treatment
     */
    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.tid +
                "\nPID: " + this.pid +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks + "\n";
    }

    /**
     * @return lockstatus
     */
    public Boolean getLockStatus() {
        return lockStatus;
    }

    /**
     * @param lockStatus
     * Sets new lock status
     */
    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }
}