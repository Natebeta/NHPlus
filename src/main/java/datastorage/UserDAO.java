package datastorage;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User> {

    public UserDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(User user) {
        return String.format("INSERT INTO user (firstname, surname, username, password, authorization, locked) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                user.getFirstName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getAuthorization(), user.isLocked());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM user WHERE uid = %d", key);
    }

    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        User u = null;
        u = new User(result.getInt(1), result.getString(2),
                result.getString(3), result.getString(4), result.getString(5),
                result.getString(6), result.getBoolean(7));
        return u;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM user";
    }

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

    @Override
    protected String getUpdateStatementString(User user) {
        return String.format("UPDATE user SET firstname = '%s', surname = '%s', username = '%s', password = '%s', " +
                        "authorization = '%s', locked = '%s' WHERE uid = %d", user.getFirstName(), user.getSurname(), user.getUsername(),
                user.getPassword(), user.getAuthorization(), user.isLocked(), user.getUid());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM user WHERE uid=%d", key);
    }
}
