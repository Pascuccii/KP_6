package by.command.admin;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.entity.RoleType;
import by.exception.ServiceException;
import by.service.impl.AdminServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Change role command.
 */
public class ChangeRoleCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter(JspAttribute.USER_ID));
        RoleType role = RoleType.getRoleByString(request.getParameter(JspAttribute.ROLE));
        String page;
        try {
            AdminServiceImpl service = new AdminServiceImpl();
            service.changeUserRole(userId, role);
            logger.info("User role with id " + userId + " changed to " + role.toString().toLowerCase());
            page = JspAddress.CHANGE_SUCCESS;
        } catch (ServiceException e) {
            logger.error("Service error occurred", e);
            page = JspAddress.ERROR_PAGE;
        }
        return new CommandResult(page, true);
    }
}
