package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;;

/**
 * Deletes a property identified using it's displayed index from the address book.
 */
public class DeletePropertyCommand extends Command {

    public static final String COMMAND_WORD = "deleteproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the property identified by the index number used in the displayed property list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Deleted Property: %1$s";

    private final String targetId;

    public DeletePropertyCommand(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();

        if (!lastShownList.stream().anyMatch(property -> property.getId().equals(targetId))) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID);
        }

        Property propertyToDelete = lastShownList.stream()
                .filter(property -> property.getId().equals(targetId))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID));
        model.deleteProperty(propertyToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PROPERTY_SUCCESS, propertyToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeletePropertyCommand otherDeleteCommand = (DeletePropertyCommand) other;
        return targetId.equals(otherDeleteCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
