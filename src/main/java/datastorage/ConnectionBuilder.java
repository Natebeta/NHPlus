package datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Establishes a connection to an HSQL database
 */
public class ConnectionBuilder {
    private static Connection conn;

    /**
     * Builds the connection to an HSQL database
     */
    private ConnectionBuilder() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));

            conn = DriverManager.getConnection("jdbc:hsqldb:db/nursingHomeDB;user=SA;password=SA");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Treiberklasse konnte nicht gefunden werden!");
        } catch (SQLException e) {
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection
     * @return conn the connection
     */
    public static Connection getConnection() {
        if (conn == null) {
            new ConnectionBuilder();
        }
        return conn;
    }

    /**
     * Closes the connection
     */
    public static void closeConnection() {
        try {
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
