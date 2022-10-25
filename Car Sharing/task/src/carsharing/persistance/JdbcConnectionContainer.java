package carsharing.persistance;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnectionContainer {

    private static final String JDBC_DRIVER = "org.h2.Driver";

    private static String dbUri = null;
    private static Connection connection = null;

    public static void setDbUri(String dbUri) {
        JdbcConnectionContainer.dbUri = dbUri;
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(dbUri);
                connection.setAutoCommit(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (JdbcConnectionContainer.connection != null) {
            try {
                JdbcConnectionContainer.connection.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            JdbcConnectionContainer.connection = null;
        }
    }
}
