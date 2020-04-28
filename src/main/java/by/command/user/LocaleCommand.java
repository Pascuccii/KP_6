package by.command.user;

import by.command.CommandResult;
import by.command.JspAddress;
import by.command.JspAttribute;
import by.command.ActionCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Locale command.
 */
public class LocaleCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter(JspAttribute.LANGUAGE);
        request.getSession().setAttribute(JspAttribute.LOCAL, lang);
        logger.info("The locale attribute was set to: " + lang);
        return new CommandResult(JspAddress.HOME_PAGE);
    }
}
