package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Utility class to encapsulate a link and unlink command as a single command.
 */
public class LinkUnlinkCommandUtil extends Command {

    private final LinkCommand linkCommand;
    private final UnlinkCommand unlinkCommand;

    /**
     * Default constructor for LinkUnlinkCommandUtil
     */
    public LinkUnlinkCommandUtil(LinkCommand linkCommand, UnlinkCommand unlinkCommand) {
        this.linkCommand = linkCommand;
        this.unlinkCommand = unlinkCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CommandResult linkCommandResult = linkCommand.execute(model);
        CommandResult unlinkCommandResult = unlinkCommand.execute(model);

        return new CommandResult(
                linkCommandResult.getFeedbackToUser() + "\n" + unlinkCommandResult.getFeedbackToUser());
    }
}
