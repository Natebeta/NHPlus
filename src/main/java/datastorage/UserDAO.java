package datastorage;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class UserDAO extends DAOimp<User> {

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public UserDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given treatment
     * @param user for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(User user) {
        return String.format("INSERT INTO user (firstname, surname, username, password, authorization, locked) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                user.getFirstName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getAuthorization(), user.isLocked());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM user WHERE uid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Treatment</code>
     * @param result ResultSet with a single row. Columns will be mapped to a user-object.
     * @return user with the data from the resultSet.
     */
    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        User u = null;
        u = new User(result.getInt(1), result.getString(2),
                result.getString(3), result.getString(4), result.getString(5),
                result.getString(6), result.getBoolean(7));
        return u;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all users.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM user";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>User-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to user-object.
     * @return ArrayList with users from the resultSet.
     */
    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<User>();
        User u = null;
        while (result.next()) {
            u = new User(result.getInt(1), result.getString(2),
                    result.getString(3), result.getString(4), result.getString(5),
                    result.getString(6), result.getBoolean(7));
            list.add(u);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given user
     * @param user for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(User user) {
        return String.format("UPDATE user SET firstname = '%s', surname = '%s', username = '%s', password = '%s', " +
                        "authorization = '%s', locked = '%s' WHERE uid = %d", user.getFirstName(), user.getSurname(), user.getUsername(),
                user.getPassword(), user.getAuthorization(), user.isLocked(), user.getUid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM user WHERE uid=%d", key);
    }
}
