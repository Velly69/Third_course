package connection;

import servlets.RegistrationServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;


public class DatabaseConnection {
    private static DatabaseConnection cp = new DatabaseConnection();
    private static final Logger log = Logger.getLogger(RegistrationServlet.class.getName());

    private final String url = "jdbc:postgresql://localhost:5432/internetshop";
    private final String user = "postgres";
    private final String password = "postgres";
    private final int MAX_CONNECTIONS = 8;

    private BlockingQueue<Connection> connections;

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.warning("JDBC Driver not found");
        }
        connections = new LinkedBlockingQueue<Connection>(MAX_CONNECTIONS);
        try {
            for (int i = 0; i < MAX_CONNECTIONS; ++i) {
                connections.put(DriverManager.getConnection(url, user, password));
            }
        } catch (SQLException e) {
            log.warning("Troubles with database");
        } catch (InterruptedException e) {
            log.warning("Connection was interrupted");
        }

    }

    public static DatabaseConnection getConnectionPool() {
        return cp;
    }

    public Connection getConnection() throws InterruptedException, SQLException {
        Connection c = connections.take();
        if (c.isClosed()) {
            c = DriverManager.getConnection(url, user, password);
        }
        return c;
    }

    public void releaseConnection(Connection c) throws InterruptedException {
        connections.put(c);
    }
}

