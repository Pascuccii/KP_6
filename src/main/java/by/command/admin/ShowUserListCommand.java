package by.command.admin;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.entity.User;
import by.exception.ServiceException;
import by.service.impl.AdminServiceImpl;
import by.command.JspAddress;
import by.command.JspAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Show user list command.
 */
public class ShowUserListCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String page;
        try {
            AdminServiceImpl service = new AdminServiceImpl();
            List<User> users = service.showUserList();
            session.setAttribute(JspAttribute.USERS, users);
            logger.info("Information about all users is provided");
            page = JspAddress.USER_LIST;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page);
    }
}
