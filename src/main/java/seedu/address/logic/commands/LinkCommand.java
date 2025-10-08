package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;

import java.util.List;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;
import seedu.address.model.property.Property;

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

    public static final String MESSAGE_LINK_PROPERTY_SUCCESS = "Linked Property ID: %1$s to Person ID: %2$s";
    public static final String MESSAGE_PERSON_ALREADY_LINKED = "Person already linked to this property.";
    public static final String MESSAGE_PROPERTY_ALREADY_LINKED = "Property already linked to this person.";

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
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Property> lastShownPropertyList = model.getFilteredPropertyList();

        Person targetPerson = linkDescriptor.getPersonInList(lastShownPersonList);
        Property targetProperty = linkDescriptor.getPropertyInList(lastShownPropertyList);

        Person updatedPerson = linkDescriptor.getUpdatedPerson(lastShownPersonList, lastShownPropertyList);
        Property updatedProperty = linkDescriptor.getUpdatedProperty(lastShownPersonList, lastShownPropertyList);

        model.setPerson(targetPerson, updatedPerson);
        model.setProperty(targetProperty, updatedProperty);

        return new CommandResult(String.format(MESSAGE_LINK_PROPERTY_SUCCESS, linkDescriptor.getPersonId(),
                linkDescriptor.getPropertyId()));
    }

    /**
     * Stores Ids and relationship to link a property to a person.
     */
    public static class LinkDescriptor {
        private Uuid personId;
        private String propertyId;
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

        public void setPersonId(Uuid personId) {
            this.personId = personId;
        }

        public Uuid getPersonId() {
            return personId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getRelationship() {
            return relationship;
        }

        /**
         * Returns the {@code Person} in the list with the matching personId.
         *
         * @throws CommandException if no such person exists in the list.
         */
        public Person getPersonInList(List<Person> personList) throws CommandException {
            return personList.stream().filter(predicate -> predicate.getUuid().equals(personId)).findAny()
                    .orElseThrow(() -> new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        }

        /**
         * Returns the {@code Property} in the list with the matching propertyId.
         *
         * @throws CommandException if no such property exists in the list.
         */
        public Property getPropertyInList(List<Property> propertyList) throws CommandException {
            return propertyList.stream().filter(predicate -> predicate.getId().equals(propertyId)).findAny()
                    .orElseThrow(() -> new CommandException(MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX));
        }

        /**
         * Returns an edited {@code Person} with the property linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public Person getUpdatedPerson(List<Person> personList, List<Property> propertyList) throws CommandException {
            Person personToEdit = getPersonInList(personList);
            switch (relationship) {
            case "buyer":
                Set<String> updatedBuyingPropertyIds = personToEdit.getBuyingPropertyIds();
                updatedBuyingPropertyIds.add(propertyId);
                return new Person(personToEdit.getUuid(), personToEdit.getName(), personToEdit.getPhone(),
                        personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(),
                        personToEdit.getBudgetMin(), personToEdit.getBudgetMax(), personToEdit.getNotes(),
                        personToEdit.getStatus(), updatedBuyingPropertyIds, personToEdit.getSellingPropertyIds());
            case "seller":
                Set<String> updatedSellingPropertyIds = personToEdit.getSellingPropertyIds();
                updatedSellingPropertyIds.add(propertyId);
                return new Person(personToEdit.getUuid(), personToEdit.getName(), personToEdit.getPhone(),
                        personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(),
                        personToEdit.getBudgetMin(), personToEdit.getBudgetMax(), personToEdit.getNotes(),
                        personToEdit.getStatus(), personToEdit.getBuyingPropertyIds(), updatedSellingPropertyIds);
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
            }
        }

        /**
         * Returns an edited {@code Property} with the person linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public Property getUpdatedProperty(List<Person> personList, List<Property> propertyList)
                throws CommandException {
            Property propertyToEdit = getPropertyInList(propertyList);
            switch (relationship) {
            case "buyer":
                Set<Uuid> updatedBuyingPersonIds = Set.copyOf(propertyToEdit.getBuyingPersonIds());
                updatedBuyingPersonIds.add(personId);
                propertyToEdit.setBuyingPersonIds(updatedBuyingPersonIds);
                return propertyToEdit;
            case "seller":
                Set<Uuid> updatedSellingPersonIds = Set.copyOf(propertyToEdit.getSellingPersonIds());
                updatedSellingPersonIds.add(personId);
                propertyToEdit.setSellingPersonIds(updatedSellingPersonIds);
                return propertyToEdit;
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
            }
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
