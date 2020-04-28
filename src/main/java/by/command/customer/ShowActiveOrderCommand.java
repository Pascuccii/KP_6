package by.command.customer;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.entity.Order;
import by.entity.User;
import by.exception.ServiceException;
import by.service.impl.CustomerServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Show active order command.
 */
public class ShowActiveOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);
        int userId = user.getId();
        String page;
        try {
            CustomerServiceImpl service = new CustomerServiceImpl();
            List<Order> orders = service.showActiveDelivery(userId);
            logger.info("Active orders of user with login " + user.getLogin() + " provided");
            List<Order> sortedOrders = service.sortListOfOrdersByOrderId(orders);
            logger.info("Active orders are sorted by id");
            session.setAttribute(JspAttribute.ORDERS, sortedOrders);
            page = JspAddress.CUSTOMER_DELIVERY;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page);
    }
}
