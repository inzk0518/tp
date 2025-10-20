package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.OwnerMatchesPredicate;
import seedu.address.model.uuid.Uuid;
import seedu.address.ui.MainWindow;

/**
 * Finds and lists all properties where the specified client is the owner.
 *
 * Note: This code currently only shows properties where client is the owner only.
 * Future enhancement: Show all properties linked to client (as buyer, seller, etc.)
 * when property-client association model is implemented.
 */
public class ShowPropertiesCommand extends Command {

    public static final String COMMAND_WORD = "showproperties";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all properties owned by the specified client.\n"
            + "Parameters: c/CLIENT_UUID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " c/123";

    public static final String MESSAGE_SUCCESS = "Listed %2$d propert%3$s owned by client UUID: %1$s";

    public static final String MESSAGE_NO_PROPERTIES =
            "No properties found owned by client UUID: %1$s\n"
                    + "Possible reasons:\n"
                    + "  • The client exists but doesn't own any properties yet\n"
                    + "  • The client UUID doesn't exist (use 'list' to verify)\n"
                    + "Tip: Use 'addproperty ... owner/%1$s' to add a property for this client.";

    private final Uuid clientUuid;

    /**
     * Creates a ShowPropertiesCommand to find all properties owned by the specified client.
     *
     * @param clientUuid The UUID of the client to search for.
     */
    public ShowPropertiesCommand(Uuid clientUuid) {
        requireNonNull(clientUuid);
        this.clientUuid = clientUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Filter properties where owner matches the client UUID
        OwnerMatchesPredicate predicate = new OwnerMatchesPredicate(clientUuid);
        model.updateFilteredPropertyList(predicate);

        //Toggle from clients list to property list
        MainWindow.getInstance().showPropertiesView();

        int numPropertiesFound = model.getFilteredPropertyList().size();

        if (numPropertiesFound == 0) {
            return new CommandResult(String.format(MESSAGE_NO_PROPERTIES, clientUuid));
        }

        // Grammatically correct: "1 property" vs "2 properties"
        String propertyWord = numPropertiesFound == 1 ? "y" : "ies";
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, clientUuid, numPropertiesFound, propertyWord));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ShowPropertiesCommand)) {
            return false;
        }

        ShowPropertiesCommand otherCommand = (ShowPropertiesCommand) other;
        return clientUuid.equals(otherCommand.clientUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientUuid", clientUuid)
                .toString();
    }
}
