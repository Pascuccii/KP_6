package by.epam.trainint.connection;

import by.connection.ConnectionPool;
import by.connection.ProxyConnection;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The type Connection pool test.
 */
public class ConnectionPoolTest {
    private static ConnectionPool pool = ConnectionPool.getInstance();
    private ProxyConnection connection;

    /**
     * Init connection pool.
     */
    @Test
    public void initConnectionPool() {
        Assert.assertNotNull(pool);
    }

    /**
     * Take connection.
     */
    @Test
    public void takeConnection() {
        connection = pool.takeConnection();
        int freeConnectionsSize = pool.getFreeConnectionsSize();
        Assert.assertEquals(15, freeConnectionsSize);
    }
}
