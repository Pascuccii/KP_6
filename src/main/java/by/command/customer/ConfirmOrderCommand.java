package by.command.customer;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.entity.Order;
import by.entity.Transport;
import by.entity.User;
import by.service.impl.CustomerServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Confirm order command.
 */
public class ConfirmOrderCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String subject = request.getParameter(JspAttribute.SUBJECT)
                .replace("<", "")
                .replace("script", "")
                .replace(">", "");
        Transport transport = Transport.getTransportByString(request.getParameter(JspAttribute.TRANSPORT));
        boolean rate = Boolean.parseBoolean(request.getParameter(JspAttribute.RATE));
        String distanceString = request.getParameter(JspAttribute.DISTANCE);
        int distance = Integer.parseInt(distanceString);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(JspAttribute.USER);
        Order order = new Order(subject, user, transport, rate, distance);
        CustomerServiceImpl service = new CustomerServiceImpl();
        Order resultOrder = service.countTotalCost(order);
        logger.info("Order price with id " + order.getOrderId() + " calculated");
        session.setAttribute(JspAttribute.ORDER, resultOrder);
        return new CommandResult(JspAddress.CONFIRM_ORDER);
    }
}
