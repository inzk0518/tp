package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.MarkUnsoldCommand.getInvalidPropertyIdsMessage;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.Status;
import seedu.address.model.uuid.Uuid;

/**
 * This command finds properties by their IDs and marks them as sold.
 * If a property with the given ID does not exist, the command throws a {@link CommandException}.
 * <p>
 *  Usage example:
 *  <pre>
 *  sold p/14 p/27
 *  </pre>
 */
public class MarkSoldCommand extends Command {

    public static final String COMMAND_WORD = "sold";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks one or more properties as sold.\n"
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "UUID...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "14 "
            + PREFIX_PROPERTY_ID + "27";

    public static final String MESSAGE_MARK_SOLD_SUCCESS = "Marked %d property(ies) as sold.";

    private final Set<Uuid> propertyIds;

    /**
     * Constructs a {@code MarkSoldCommand} with the specified list of property IDs.
     *
     * @param propertyIds The IDs of the properties to mark as sold. Must not be null.
     */
    public MarkSoldCommand(Set<Uuid> propertyIds) {
        requireNonNull(propertyIds);
        this.propertyIds = propertyIds;
    }

    /**
     * Executes the command to mark the specified properties as sold.
     *
     * @param model The model which contains the property book and data manipulation methods.
     * @return A {@link CommandResult} indicating the outcome of the command.
     * @throws CommandException If any of the property IDs do not exist in the model.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String invalidIdsMessage = getInvalidPropertyIdsMessage(model, propertyIds);
        if (invalidIdsMessage != null) {
            throw new CommandException(invalidIdsMessage);

        }

        int count = 0;
        for (Uuid id : propertyIds) {
            Property property = model.getPropertyById(id);
            Property updated = new Property(
                    property.getUuid(),
                    property.getPropertyAddress(),
                    property.getBathroom(),
                    property.getBedroom(),
                    property.getFloorArea(),
                    property.getListing(),
                    property.getPostal(),
                    property.getPrice(),
                    new Status("unavailable"),
                    property.getType(),
                    property.getOwner(),
                    property.getBuyingContactIds(),
                    property.getSellingContactIds()
            );
            model.setProperty(property, updated);
            count++;
        }

        showPropertiesView();

        return new CommandResult(String.format(MESSAGE_MARK_SOLD_SUCCESS, count));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkSoldCommand
                && propertyIds.equals(((MarkSoldCommand) other).propertyIds));
    }
}
