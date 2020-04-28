package by.command.admin;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.entity.Transport;
import by.exception.ServiceException;
import by.service.impl.AdminServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Change transport command.
 */
public class ChangeTransportCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter(JspAttribute.USER_ID));
        Transport transport = Transport.getTransportByString(request.getParameter(JspAttribute.TRANSPORT));
        String page;
        try {
            AdminServiceImpl service = new AdminServiceImpl();
            service.changeUserTransport(userId, transport);
            logger.info("User transport with id " + userId + " changed to " + transport.toString().toLowerCase());
            page = JspAddress.CHANGE_SUCCESS;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page, true);
    }
}
