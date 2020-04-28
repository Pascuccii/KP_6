package by.service.impl;

import by.dao.impl.CourierDaoImpl;
import by.entity.Order;
import by.entity.User;
import by.exception.DaoException;
import by.exception.ServiceException;
import by.service.CourierService;
import by.util.ComparatorByOrderId;

import java.util.List;

/**
 * The type Courier service.
 */
public class CourierServiceImpl implements CourierService {
    private CourierDaoImpl courierDao = new CourierDaoImpl();

    @Override
    public List<Order> showAvailableDelivery(User courier) throws ServiceException {
        try {
            return courierDao.selectAvailableDelivery(courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateOrderStatusToProcessing(int orderId, User courier) throws ServiceException {
        try {
            courierDao.changeOrderStatusToProcessing(orderId, courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> showProcessingDelivery(User courier) throws ServiceException {
        try {
            return courierDao.selectProcessingDelivery(courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateOrderStatusToComplete(int orderId, User courier) throws ServiceException {
        try {
            courierDao.changeOrderStatusToComplete(orderId, courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> showCompleteDelivery(User courier) throws ServiceException {
        try {
            return courierDao.selectCompleteDelivery(courier);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> sortListOfOrdersByOrderId(List<Order> orders) {
        orders.sort(new ComparatorByOrderId());
        return orders;
    }
}
