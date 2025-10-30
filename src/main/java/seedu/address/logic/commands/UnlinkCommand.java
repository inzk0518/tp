package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_ID;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Unlinks properties from contacts.
 */
public class UnlinkCommand extends Command {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": unlinks properties from contacts.\n"
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "PROPERTY_ID... "
            + PREFIX_CONTACT_ID + "CONTACT_ID...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "2 "
            + PREFIX_PROPERTY_ID + "5 "
            + PREFIX_CONTACT_ID + "3";

    public static final String MESSAGE_UNLINK_SUCCESS =
            "Unlinked Property IDs: %1$s with Contact IDs: %2$s";

    private static final Logger logger = Logger.getLogger(UnlinkCommand.class.getName());

    private final UnlinkDescriptor unlinkDescriptor;

    /**
     * Creates an UnlinkCommand to unlink the specified {@code Property} to the specified {@code Contact}
     */
    public UnlinkCommand(UnlinkDescriptor unlinkDescriptor) {
        requireNonNull(unlinkDescriptor);
        this.unlinkDescriptor = unlinkDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownContactList = model.getFilteredContactList();
        List<Property> lastShownPropertyList = model.getFilteredPropertyList();

        unlinkDescriptor.throwExceptionIfUnlinked(lastShownContactList, lastShownPropertyList);

        List<Contact> targetContacts = unlinkDescriptor.getContactsInList(lastShownContactList);
        List<Property> targetProperties = unlinkDescriptor.getPropertiesInList(lastShownPropertyList);

        List<Contact> updatedContacts = unlinkDescriptor.getUpdatedContacts(lastShownContactList);
        List<Property> updatedProperties = unlinkDescriptor.getUpdatedProperties(lastShownPropertyList);

        Stream.iterate(0, x -> x < targetContacts.size(), x -> x + 1)
                .forEach(i -> model.setContact(targetContacts.get(i), updatedContacts.get(i)));
        Stream.iterate(0, x -> x < targetProperties.size(), x -> x + 1)
                .forEach(i -> model.setProperty(targetProperties.get(i), updatedProperties.get(i)));

        logger.log(Level.FINER, "Successfully unlinked contacts and properties");

        return new CommandResult(String.format(MESSAGE_UNLINK_SUCCESS, unlinkDescriptor.getPropertyIds(),
                    unlinkDescriptor.getContactIds()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnlinkCommand)) {
            return false;
        }

        UnlinkCommand otherUnlinkCommand = (UnlinkCommand) other;
        return unlinkDescriptor.equals(otherUnlinkCommand.unlinkDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("unlinkDescriptor", unlinkDescriptor)
                .toString();
    }

    /**
     * Stores Ids to unlink a property to a contact.
     */
    public static class UnlinkDescriptor {
        private Set<Uuid> contactIds;
        private Set<Uuid> propertyIds;

        public UnlinkDescriptor() {}

        /**
         * Copy constructor.
         */
        public UnlinkDescriptor(UnlinkDescriptor toCopy) {
            setContactIds(toCopy.contactIds);
            setPropertyIds(toCopy.propertyIds);
        }

        public void setContactIds(Set<Uuid> contactIds) {
            this.contactIds = contactIds;
        }

        public Set<Uuid> getContactIds() {
            return contactIds;
        }

        public void setPropertyIds(Set<Uuid> propertyIds) {
            this.propertyIds = propertyIds;
        }

        public Set<Uuid> getPropertyIds() {
            return propertyIds;
        }

        /**
         * Returns the {@code List<Contact>} in the list with all matching contactIds.
         *
         * @throws CommandException if any contact is not found in the list.
         */
        public List<Contact> getContactsInList(List<Contact> contactList) throws CommandException {
            assert (contactList != null);
            List<Contact> contactsList = new ArrayList<>(contactList).stream()
                    .filter(contact -> contactIds.contains(contact.getUuid()))
                    .collect(Collectors.toList());
            if (contactsList.size() != contactIds.size()) {
                throw new CommandException(MESSAGE_INVALID_CONTACT_DISPLAYED_ID);
            }
            return contactsList;
        }

        /**
         * Returns the {@code List<Property>} in the list with the matching propertyId.
         *
         * @throws CommandException if any property is not found in the list.
         */
        public List<Property> getPropertiesInList(List<Property> propertyList) throws CommandException {
            assert (propertyList != null);
            List<Property> propertiesList = new ArrayList<>(propertyList).stream()
                    .filter(property -> propertyIds.contains(property.getUuid()))
                    .collect(Collectors.toList());
            if (propertiesList.size() != propertyIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PROPERTY_DISPLAYED_ID);
            }
            return propertiesList;
        }

        /**
         * Returns an edited {@code List<Contact>} with the properties unlinked.
         */
        public List<Contact> getUpdatedContacts(List<Contact> contactList) throws CommandException {
            List<Contact> contactsToEdit = getContactsInList(contactList);
            return new ArrayList<>(contactsToEdit).stream()
                    .map(contactToEdit -> contactToEdit
                    .duplicateWithNewBuyingPropertyIds(
                    contactToEdit.getBuyingPropertyIds().stream().filter(id -> !propertyIds.contains(id))
                    .collect(Collectors.toSet()))
                    .duplicateWithNewSellingPropertyIds(
                    contactToEdit.getSellingPropertyIds().stream().filter(id -> !propertyIds.contains(id))
                    .collect(Collectors.toSet())))
                    .collect(Collectors.toList());
        }

        /**
         * Returns an edited {@code List<Property>} with the contacts unlinked.
         */
        public List<Property> getUpdatedProperties(List<Property> propertyList) throws CommandException {
            List<Property> propertiesToEdit = getPropertiesInList(propertyList);
            return new ArrayList<>(propertiesToEdit).stream()
                    .map(propertyToEdit -> propertyToEdit
                    .duplicateWithNewBuyingContactIds(
                    propertyToEdit.getBuyingContactIds().stream().filter(id -> !contactIds.contains(id))
                    .collect(Collectors.toSet()))
                    .duplicateWithNewSellingContactIds(
                    propertyToEdit.getSellingContactIds().stream().filter(id -> !contactIds.contains(id))
                    .collect(Collectors.toSet())))
                    .collect(Collectors.toList());
        }

        /**
         * @throws CommandException if any of the related contacts and properties are already unlinked.
         */
        public void throwExceptionIfUnlinked(List<Contact> contactList, List<Property> propertyList)
                throws CommandException {
            List<Contact> filteredContacts = new ArrayList<>(getContactsInList(contactList));
            List<Property> filteredProperties = new ArrayList<>(getPropertiesInList(propertyList));
            boolean hasAnyContactUnlinked = filteredContacts.stream()
                    .anyMatch(contact -> Collections.disjoint(propertyIds, contact.getBuyingPropertyIds())
                            && Collections.disjoint(propertyIds, contact.getSellingPropertyIds()));
            boolean hasAnyPropertyUnlinked = filteredProperties.stream()
                    .anyMatch(property -> Collections.disjoint(contactIds, property.getBuyingContactIds())
                            && Collections.disjoint(contactIds, property.getSellingContactIds()));
            if (hasAnyContactUnlinked || hasAnyPropertyUnlinked) {
                throw new CommandException(Messages.MESSAGE_UNLINKING_ALREADY_UNLINKED);
            }
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof UnlinkDescriptor)) {
                return false;
            }

            UnlinkDescriptor otherUnlinkDescriptor = (UnlinkDescriptor) other;

            return contactIds.equals(otherUnlinkDescriptor.contactIds)
                    && propertyIds.equals(otherUnlinkDescriptor.propertyIds);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("contactIds", contactIds)
                    .add("propertyIds", propertyIds)
                    .toString();
        }
    }
}
