package datastorage;

import model.Treatment;
import utils.DateConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO extends DAOimp<Treatment> {

    public TreatmentDAO(Connection conn) {
        super(conn);
    }


    @Override
    protected String getCreateStatementString(Treatment treatment) throws SQLException {
        return String.format("INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks, lock_status, cid) VALUES " +
                        "(%d, '%s', '%s', '%s', '%s', '%s', '%s', '%d')", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks(), treatment.getLockStatus(), treatment.getCid());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE tid = %d AND lock_status = false", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Treatment</code>
     * @param result ResultSet with a single row. Columns will be mapped to a user-object.
     * @return treatment with the data from the resultSet.
     */
    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2),
                date, begin, end, result.getString(6), result.getString(7), result.getLong(9));
        return m;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM treatment WHERE lock_status = false";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Treatment-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to treatment-object.
     * @return ArrayList with user from the resultSet.
     */
    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment t = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            t = new Treatment(result.getLong(1), result.getLong(2),
                    date, begin, end, result.getString(6), result.getString(7), result.getLong(9));
            list.add(t);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given user
     * @param treatment for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Treatment treatment) throws SQLException {
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s'," +
                        "description = '%s', remarks = '%s' WHERE tid = %d", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(), treatment.getCaregiver().getSurname(),
                treatment.getTid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given treatment key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM treatment WHERE tid= %d", key);
    }


    /**
     * generates a <code>Treatment-List</code> from a given pid
     * @param pid from which a list of treatments will be created
     * @return ArrayList with treatments by patient from the resultSet.
     */
    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOnePatientByPid(pid));
        list = getListFromResultSet(result);
        return list;
    }

    /**
     * generates a <code>SELECT</code>-Statement for a list of treatments by patient
     * @param pid for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    private String getReadAllTreatmentsOfOnePatientByPid(long pid) {
        return String.format("SELECT * FROM treatment WHERE pid = %d AND lock_status = false", pid);
    }

    /**
     * generates a <code>delete</code>-Statement for a given patient key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    public void deleteByPid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM treatment WHERE pid= %d", key));
    }

    public void updateLockStatus(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("UPDATE treatment SET lock_status = true WHERE tid = %d", key));
    }
}