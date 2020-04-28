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
 * The type Show done order command.
 */
public class ShowDoneOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);
        int userId = user.getId();
        String page;
        try {
            CustomerServiceImpl service = new CustomerServiceImpl();
            List<Order> orders = service.showCompleteDelivery(userId);
            logger.info("Completed orders of user with login " + user.getLogin() + " provided");
            List<Order> sortedOrders = service.sortListOfOrdersByOrderId(orders);
            logger.info("Completed orders are sorted by id");
            session.setAttribute(JspAttribute.ORDERS, sortedOrders);
            page = JspAddress.CUSTOMER_DONE;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page);
    }
}
