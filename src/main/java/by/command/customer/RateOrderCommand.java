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
 * The type Rate order command.
 */
public class RateOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter(JspAttribute.ORDER_ID));
        String page;
        try {
            CustomerServiceImpl service = new CustomerServiceImpl();
            Order order = service.showCurrentDelivery(orderId);
            logger.info("Order information with id " + orderId + " provided");
            session.setAttribute(JspAttribute.ORDER, order);
            page = JspAddress.RATE_ORDER;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page);
    }
}
