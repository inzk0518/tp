package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.AssociatedWithPropertyPredicate;
import seedu.address.model.uuid.Uuid;
import seedu.address.ui.MainWindow;

/**
 * Creates a ShowClientsCommand to list all clients associated with the specified property UUID.
 */
public class ShowClientsCommand extends Command {

    public static final String COMMAND_WORD = "showclients";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all clients associated with the specified property.\n"
            + "Parameters: p/PROPERTY_UUID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " p/123";

    public static final String MESSAGE_SUCCESS = "Listed %2$d client%3$s associated with property UUID: %1$s";

    public static final String MESSAGE_NO_CLIENTS =
            "No clients found associated with property UUID: %1$s\n"
                    + "Possible reasons:\n"
                    + "  • The property exists but has no linked clients yet\n"
                    + "  • The property UUID doesn't exist (use 'listproperties' to verify)\n"
                    + "Tip: Use 'link p/%1$s c/CLIENT_UUID' to associate clients with this property.";

    private final Uuid propertyUuid;

    /**
     * Creates a ShowClientsCommand to list all clients associated with the specified property UUID.
     *
     * @param propertyUuid The UUID of the property whose associated clients will be shown.
     */
    public ShowClientsCommand(Uuid propertyUuid) {
        requireNonNull(propertyUuid);
        this.propertyUuid = propertyUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Create predicate using all properties in the model
        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(propertyUuid, model.getPropertyBook().getPropertyList());
        model.updateFilteredPersonList(predicate);

        // Switch view to show contacts
        showContactsView();

        int numClientsFound = model.getFilteredPersonList().size();

        if (numClientsFound == 0) {
            return new CommandResult(String.format(MESSAGE_NO_CLIENTS, propertyUuid));
        }

        String pluralSuffix = numClientsFound == 1 ? "" : "s";
        return new CommandResult(String.format(MESSAGE_SUCCESS, propertyUuid, numClientsFound, pluralSuffix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ShowClientsCommand
                && propertyUuid.equals(((ShowClientsCommand) other).propertyUuid));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("propertyUuid", propertyUuid)
                .toString();
    }
}
