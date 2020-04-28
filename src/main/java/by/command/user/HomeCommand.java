package by.command.user;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The type Home command.
 */
public class HomeCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(JspAttribute.PAGE, JspAddress.HOME_PAGE);
        logger.info("Go to homepage");
        return new CommandResult(JspAddress.HOME_PAGE);
    }
}
