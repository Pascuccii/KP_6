package by.command.admin;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.entity.User;
import by.exception.ServiceException;
import by.service.impl.AdminServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Edit user command.
 */
public class EditUserCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int userId = Integer.parseInt(request.getParameter(JspAttribute.USER_ID));
        String page;
        try {
            AdminServiceImpl service = new AdminServiceImpl();
            User user = service.showCurrentUser(userId);
            session.setAttribute(JspAttribute.CURRENT_USER, user);
            logger.info("User information with id " + userId + " provided");
            page = JspAddress.EDIT_USER;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page);
    }
}
