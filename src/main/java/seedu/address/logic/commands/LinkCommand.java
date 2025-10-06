package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Links a property to a person.
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a property to a person. "
            + "Parameters: "
            + PREFIX_LINK_PROPERTY_ID + "PROPERTY_INDEX "
            + PREFIX_LINK_RELATIONSHIP + "RELATIONSHIP (must be either 'buyer' or 'seller')"
            + PREFIX_LINK_CLIENT_ID + "CLIENT_INDEX"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LINK_PROPERTY_ID + "2 "
            + PREFIX_LINK_RELATIONSHIP + "buyer "
            + PREFIX_LINK_CLIENT_ID + "3";

    private final LinkDescriptor linkDescriptor;

    /**
     * Creates a LinkCommand to link the specified {@code Property} to the specified {@code Person}
     * in the address book.
     */
    public LinkCommand(LinkDescriptor linkDescriptor) {
        requireNonNull(linkDescriptor);
        this.linkDescriptor = linkDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        throw new CommandException("LinkCommand is not yet implemented.");
    }

    /**
     * Stores Ids and relationship to link a property to a person.
     */
    public static class LinkDescriptor {
        private Index personId;
        private Index propertyId;
        private String relationship;

        public LinkDescriptor() {}

        /**
         * Copy constructor.
         */
        public LinkDescriptor(LinkDescriptor toCopy) {
            setPersonId(toCopy.personId);
            setPropertyId(toCopy.propertyId);
            setRelationship(toCopy.relationship);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(personId, propertyId, relationship);
        }

        public void setPersonId(Index personId) {
            this.personId = personId;
        }

        public Index getPersonId() {
            return personId;
        }

        public void setPropertyId(Index propertyId) {
            this.propertyId = propertyId;
        }

        public Index getPropertyId() {
            return propertyId;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getRelationship() {
            return relationship;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof LinkDescriptor)) {
                return false;
            }

            LinkDescriptor otherLinkDescriptor = (LinkDescriptor) other;

            return getPersonId().equals(otherLinkDescriptor.getPersonId())
                    && getPropertyId().equals(otherLinkDescriptor.getPropertyId())
                    && getRelationship().equals(otherLinkDescriptor.getRelationship());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("personId", personId)
                    .add("propertyId", propertyId)
                    .add("relationship", relationship)
                    .toString();
        }
    }
}
