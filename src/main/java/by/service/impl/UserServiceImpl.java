package by.service.impl;

import by.connection.ConnectionPool;
import by.dao.impl.UserDaoImpl;
import by.entity.User;
import by.exception.DaoException;
import by.exception.ServiceException;
import by.exception.UserExistsException;
import by.service.UserService;

/**
 * The type User service.
 */
public class UserServiceImpl implements UserService {
    private UserDaoImpl userDao = new UserDaoImpl();
    private final ConnectionPool pool;

    /**
     * Instantiates a new User service.
     */
    public UserServiceImpl() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public User logIn(String login, String password) throws ServiceException {
        try {
            return userDao.login(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User register(User user) throws ServiceException, UserExistsException {
        try {
            if (userDao.userExists(user.getLogin())) {
                throw new UserExistsException();
            }
            if (user.getTransport() == null) {
                userDao.registerCustomer(user);
                return userDao.findCustomerByLogin(user.getLogin());
            } else {
                userDao.registerCourier(user);
                return userDao.findCourierByLogin(user.getLogin());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User changePassword(String login, String oldPassword, String newPassword) throws ServiceException {
        User user = new User(login, oldPassword);
        try {
            return userDao.updateUserPassword(user, newPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
