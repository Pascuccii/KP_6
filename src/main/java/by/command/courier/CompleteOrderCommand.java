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
 * The type Complete order command.
 */
public class CompleteOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter(JspAttribute.ORDER_ID));
        User courier = (User) session.getAttribute(JspAttribute.USER);
        String page;
        try {
            CourierServiceImpl service = new CourierServiceImpl();
            service.updateOrderStatusToComplete(orderId, courier);
            logger.info("Order status from id " + orderId + " changed to completed");
            List<Order> orders = service.showCompleteDelivery(courier);
            logger.info("Completed orders of courier with login " + courier.getLogin() + " provided");
            List<Order> sortedOrders = service.sortListOfOrdersByOrderId(orders);
            logger.info("Orders are sorted by id");
            session.setAttribute(JspAttribute.ORDERS, sortedOrders);
            page = JspAddress.COMPLETE_ORDER;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page, true);
    }
}
