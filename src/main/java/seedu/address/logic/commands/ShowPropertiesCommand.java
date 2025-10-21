package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.OwnerMatchesPredicate;
import seedu.address.model.uuid.Uuid;

/**
 * Finds and lists all properties where the specified contact is the owner.
 *
 * Note: This code currently only shows properties where contact is the owner only.
 * Future enhancement: Show all properties linked to contact (as buyer, seller, etc.)
 * when property-contact association model is implemented.
 */
public class ShowPropertiesCommand extends Command {

    public static final String COMMAND_WORD = "showproperties";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all properties owned by the specified contact.\n"
            + "Parameters: c/CONTACT_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " c/123";

    public static final String MESSAGE_SUCCESS = "Listed %2$d propert%3$s owned by contact UUID: %1$s";

    public static final String MESSAGE_NO_PROPERTIES =
            "No properties found owned by contact UUID: %1$s\n"
                    + "Possible reasons:\n"
                    + "  • The contact exists but doesn't own any properties yet\n"
                    + "  • The contact UUID doesn't exist (use 'list' to verify)\n"
                    + "Tip: Use 'addproperty ... owner/%1$s' to add a property for this contact.";

    private final Uuid contactUuid;

    /**
     * Creates a ShowPropertiesCommand to find all properties owned by the specified contact.
     *
     * @param contactUuid The UUID of the contact to search for.
     */
    public ShowPropertiesCommand(Uuid contactUuid) {
        requireNonNull(contactUuid);
        this.contactUuid = contactUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Filter properties where owner matches the contact UUID
        OwnerMatchesPredicate predicate = new OwnerMatchesPredicate(contactUuid);
        model.updateFilteredPropertyList(predicate);

        int numPropertiesFound = model.getFilteredPropertyList().size();

        if (numPropertiesFound == 0) {
            return new CommandResult(String.format(MESSAGE_NO_PROPERTIES, contactUuid));
        }

        // Grammatically correct: "1 property" vs "2 properties"
        String propertyWord = numPropertiesFound == 1 ? "y" : "ies";
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, contactUuid, numPropertiesFound, propertyWord));
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
        return contactUuid.equals(otherCommand.contactUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contactId", contactUuid)
                .toString();
    }
}
