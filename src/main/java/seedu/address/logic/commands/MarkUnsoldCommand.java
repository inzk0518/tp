package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Marks one or more properties as unsold in the property book.
 * Command format: unsold p/property_id{1..}
 */
public class MarkUnsoldCommand extends Command {

    public static final String COMMAND_WORD = "unsold";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks one or more properties as unsold.\n"
            + "Parameters: p/PROPERTY_ID [p/PROPERTY_ID]...\n"
            + "Example: " + COMMAND_WORD + " p/7 p/33";

    public static final String MESSAGE_MARK_UNSOLD_SUCCESS = "Marked %d property(ies) as unsold.";
    public static final String MESSAGE_PROPERTY_NOT_FOUND = "Property with ID %s not found.";

    private final List<String> propertyIds;

    public MarkUnsoldCommand(List<String> propertyIds) {
        requireNonNull(propertyIds);
        this.propertyIds = propertyIds;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int count = 0;
        for (String id : propertyIds) {
            Property property = model.getPropertyById(id);
            if (property == null) {
                throw new CommandException(String.format(MESSAGE_PROPERTY_NOT_FOUND, id));
            }
            model.markPropertyAsUnsold(property);
            count++;
        }

        return new CommandResult(String.format(MESSAGE_MARK_UNSOLD_SUCCESS, count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkUnsoldCommand
                && propertyIds.equals(((MarkUnsoldCommand) other).propertyIds));
    }
}
