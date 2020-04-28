package by.command.courier;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.entity.Order;
import by.entity.User;
import by.exception.ServiceException;
import by.service.impl.CourierServiceImpl;
import by.command.JspAddress;
import by.command.JspAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Show completed order command.
 */
public class ShowCompletedOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User courier = (User) session.getAttribute(JspAttribute.USER);
        String page;
        try {
            CourierServiceImpl service = new CourierServiceImpl();
            List<Order> orders = service.showCompleteDelivery(courier);
            logger.info("Completed orders of courier with login " + courier.getLogin() + " provided");
            List<Order> sortedOrders = service.sortListOfOrdersByOrderId(orders);
            logger.info("Completed orders are sorted by id");
            session.setAttribute(JspAttribute.ORDERS, sortedOrders);
            page = JspAddress.COMPLETE_ORDER;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page);
    }
}
