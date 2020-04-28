package by.command.admin;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.exception.ServiceException;
import by.exception.UserExistsException;
import by.service.impl.AdminServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Change login command.
 */
public class ChangeLoginCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter(JspAttribute.USER_ID));
        String currentLogin = request.getParameter(JspAttribute.CURRENT_LOGIN);
        HttpSession session = request.getSession();
        String page;
        try {
            AdminServiceImpl service = new AdminServiceImpl();
            service.changeUserLogin(userId, currentLogin);
            logger.info("user login with id" + userId + " changed to " + currentLogin);
            page = JspAddress.CHANGE_SUCCESS;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        } catch (UserExistsException e) {
            logger.info("user with login " + currentLogin + " already exist");
            session.setAttribute(JspAttribute.USER_EXIST, JspAttribute.USER_EXIST);
            page = JspAddress.EDIT_USER;
        }
        return new CommandResult(page, true);
    }
}
