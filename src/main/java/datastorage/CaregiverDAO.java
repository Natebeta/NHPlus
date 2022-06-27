package datastorage;

import model.Caregiver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CaregiverDAO extends DAOimp<Caregiver>{

    /**
     * constructs Onbject. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public CaregiverDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(Caregiver caregiver) {
        return String.format("INSERT INTO caregiver (firstname, surname, phonenumber, lock_status) VALUES ('%s', '%s', '%s', '%s')",
                caregiver.getFirstName(), caregiver.getSurname(), caregiver.getPhoneNumber(), caregiver.getLockStatus());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM caregiver WHERE cid = %d AND lock_status = false", key);
    }

    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        Caregiver c = null;
        c = new Caregiver(result.getInt(1), result.getString(2),
                result.getString(3), result.getString(4), result.getBoolean(5));
        return c;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM caregiver WHERE lock_status = false";
    }

    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<Caregiver>();
        Caregiver c = null;
        while (result.next()) {
            c = new Caregiver(result.getInt(1), result.getString(2),
                    result.getString(3), result.getString(4), result.getBoolean(5));
            list.add(c);
        }
        return list;
    }

    @Override
    protected String getUpdateStatementString(Caregiver caregiver) {
        return String.format("UPDATE caregiver SET firstname = '%s', surname = '%s', phonenumber = '%s' WHERE cid = %d",
                caregiver.getFirstName(), caregiver.getSurname(), caregiver.getPhoneNumber(), caregiver.getCid());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM caregiver WHERE cid=%d", key);
    }

    public void updateLockStatus(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("UPDATE caregiver SET lock_status = true WHERE cid = %d", key));
    }
}
