package by.command.user;

import by.command.ActionCommand;
import by.command.CommandResult;
import by.command.JspAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Empty command.
 */
public class EmptyCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.warn("Unknown command entered");
        return new CommandResult(JspAddress.UNKNOWN_COMMAND);
    }
}
