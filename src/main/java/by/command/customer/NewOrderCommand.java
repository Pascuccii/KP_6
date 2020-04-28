package by.command.customer;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.entity.Order;
import by.entity.User;
import by.exception.ServiceException;
import by.service.impl.CustomerServiceImpl;
import by.command.JspAddress;
import by.command.JspAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type New order command.
 */
public class NewOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);
        Order order = (Order) session.getAttribute(JspAttribute.ORDER);
        String page;
        try {
            CustomerServiceImpl service = new CustomerServiceImpl();
            service.checkout(order);
            logger.info("User with login " + user.getLogin() + " placed an order with id " + order.getOrderId());
            List<Order> orders = service.showActiveDelivery(user.getId());
            logger.info("Active orders of user with login " + user.getLogin() + " provided");
            List<Order> sortedOrders = service.sortListOfOrdersByOrderId(orders);
            logger.info("Active orders are sorted by id");
            session.setAttribute(JspAttribute.ORDERS, sortedOrders);
            session.setAttribute(JspAttribute.USER, user);
            page = JspAddress.CUSTOMER_DELIVERY;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page, true);
    }
}
