package by.dao;

import by.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T extends Entity> {
    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger(BaseDao.class);

    /**
     * Close.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Couldn't close statement: ", e);
        }
    }
}
