package by.command.customer;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.entity.Order;
import by.exception.ServiceException;
import by.service.impl.CustomerServiceImpl;
import by.command.JspAddress;
import by.command.JspAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Confirm rate command.
 */
public class ConfirmRateCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int rate = Integer.parseInt(request.getParameter(JspAttribute.RATE));
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(JspAttribute.ORDER);
        int orderId = order.getOrderId();
        String courierLogin = order.getUser().getLogin();
        String page;
        try {
            CustomerServiceImpl service = new CustomerServiceImpl();
            double currentRating = service.showCurrentCourierRating(courierLogin);
            logger.info("Current courier rating with login " + courierLogin + " provided");
            double updatedRating = service.updateRating(courierLogin, currentRating, rate);
            logger.info("Courier rating with login " + courierLogin + " updated to " + rate);
            service.updateOrderStatusToRated(orderId);
            logger.info("Order status from id " + orderId + " changed to rated");
            session.setAttribute(JspAttribute.LOGIN, courierLogin);
            session.setAttribute(JspAttribute.USER_RATING, updatedRating);
            page = JspAddress.THANK_YOU_PAGE;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page, true);
    }
}
