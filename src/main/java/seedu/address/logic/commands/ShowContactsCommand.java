package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.predicates.AssociatedWithPropertyPredicate;
import seedu.address.model.uuid.Uuid;

/**
 * Creates a ShowContactsCommand to list all contacts associated with the specified property UUID.
 */
public class ShowContactsCommand extends Command {

    public static final String COMMAND_WORD = "showcontacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all contacts associated with the specified property.\n"
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "PROPERTY_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "123";

    public static final String MESSAGE_SUCCESS = "Listed %2$d contact%3$s associated with property ID: %1$s";

    public static final String MESSAGE_NO_CONTACTS = "No contacts found associated with property ID: %1$s\n"
            + "Possible reasons:\n"
            + "  • The property exists but has no linked contacts yet\n"
            + "  • The property ID doesn't exist (use '"
            + ListCommand.COMMAND_WORD + "' & '"
            + FilterContactCommand.COMMAND_WORD + "' to verify)\n"
            + "Tip: Use '"
            + LinkCommand.COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "%1$s "
            + PREFIX_CONTACT_ID + "CONTACT_ID "
            + PREFIX_LINK_RELATIONSHIP + "RELATIONSHIP' to associate contacts with this property.";

    private final Uuid propertyUuid;

    /**
     * Creates a ShowContactsCommand to list all contacts associated with the specified property UUID.
     *
     * @param propertyUuid The UUID of the property whose associated contacts will be shown.
     */
    public ShowContactsCommand(Uuid propertyUuid) {
        requireNonNull(propertyUuid);
        this.propertyUuid = propertyUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Create predicate using all properties in the model
        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(propertyUuid, model.getPropertyBook().getPropertyList());
        model.updateFilteredContactList(predicate);

        // Switch view to show contacts
        showContactsView();

        int numContactsFound = model.getFilteredContactList().size();

        if (numContactsFound == 0) {
            return new CommandResult(String.format(MESSAGE_NO_CONTACTS, propertyUuid));
        }

        String pluralSuffix = numContactsFound == 1 ? "" : "s";
        return new CommandResult(String.format(MESSAGE_SUCCESS, propertyUuid, numContactsFound, pluralSuffix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ShowContactsCommand
                && propertyUuid.equals(((ShowContactsCommand) other).propertyUuid));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("propertyUuid", propertyUuid)
                .toString();
    }
}
