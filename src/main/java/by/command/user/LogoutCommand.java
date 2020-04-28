package by.command.user;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Logout command.
 */
public class LogoutCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        logger.info("Session with id " + request.getSession().getId() + " was invalidate");
        return new CommandResult(JspAddress.HOME_PAGE);
    }
}
