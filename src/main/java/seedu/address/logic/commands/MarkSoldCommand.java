package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Marks one or more properties as sold in the property book.
 * Command format: sold p/property_id{1..}
 */
public class MarkSoldCommand extends Command {

    public static final String COMMAND_WORD = "sold";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks one or more properties as sold.\n"
            + "Parameters: p/PROPERTY_ID [p/PROPERTY_ID]...\n"
            + "Example: " + COMMAND_WORD + " p/14 p/27";

    public static final String MESSAGE_MARK_SOLD_SUCCESS = "Marked %d property(ies) as sold.";
    public static final String MESSAGE_PROPERTY_NOT_FOUND = "Property with ID %s not found.";

    private final List<String> propertyIds;

    public MarkSoldCommand(List<String> propertyIds) {
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
            model.markPropertyAsSold(property);
            count++;
        }

        return new CommandResult(String.format(MESSAGE_MARK_SOLD_SUCCESS, count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkSoldCommand
                && propertyIds.equals(((MarkSoldCommand) other).propertyIds));
    }
}
