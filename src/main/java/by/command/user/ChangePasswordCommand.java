package by.command.user;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.entity.User;
import by.exception.ServiceException;
import by.service.impl.UserServiceImpl;
import by.command.JspAddress;
import by.command.JspAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Change password command.
 */
public class ChangePasswordCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User userFromSession = (User) session.getAttribute(JspAttribute.USER);
        String login = userFromSession.getLogin();
        String oldPassword = request.getParameter(JspAttribute.OLD_PASSWORD);
        String newPassword = request.getParameter(JspAttribute.NEW_PASSWORD);
        String confirmPassword = request.getParameter(JspAttribute.CONFIRM_PASSWORD);
        String page;
        try {
            if (!oldPassword.equals(newPassword)) {
                if (newPassword.equals(confirmPassword)) {
                    UserServiceImpl service = new UserServiceImpl();
                    User user = service.changePassword(login, oldPassword, newPassword);
                    if (user != null) {
                        logger.info("User password with login " + login + " changed");
                        session.setAttribute(JspAttribute.USER, user);
                        session.setAttribute(JspAttribute.MESSAGE, JspAttribute.CHANGED_PASSWORD);
                    } else {
                        logger.info("User password with login " + login + " not changed: wrong old password");
                        session.setAttribute(JspAttribute.WRONG_DATA, JspAttribute.WRONG_PASSWORD);
                    }
                } else {
                    logger.info("User password with login " + login + " not changed: new and confirm passwords do not match");
                    session.setAttribute(JspAttribute.PASSWORD_DOES_NOT_MATCH, JspAttribute.PASSWORD_DOES_NOT_MATCH);
                }
            } else {
                logger.info("User password with login " + login + " not changed: new and old passwords do not match");
                session.setAttribute(JspAttribute.PASSWORDS_EQUALS, JspAttribute.PASSWORDS_EQUALS);
            }
            page = JspAddress.CHANGE_PASSWORD;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.CHANGE_PASSWORD;
        }
        session.setAttribute(JspAttribute.PAGE, page);
        return new CommandResult(page, true);
    }
}
