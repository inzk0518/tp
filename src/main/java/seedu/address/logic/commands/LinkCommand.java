package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Links properties to people.
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links properties to people. "
            + "Parameters: "
            + PREFIX_LINK_PROPERTY_ID + "PROPERTY_ID{1..} "
            + PREFIX_LINK_RELATIONSHIP + "RELATIONSHIP (must be either 'buyer' or 'seller')"
            + PREFIX_LINK_CLIENT_ID + "CLIENT_ID{1..}"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LINK_PROPERTY_ID + "2 "
            + PREFIX_LINK_RELATIONSHIP + "buyer "
            + PREFIX_LINK_CLIENT_ID + "3 "
            + PREFIX_LINK_CLIENT_ID + "5";

    public static final String MESSAGE_LINK_BUYER_SUCCESS =
            "Linked Property IDs: %1$s with Person IDs: %2$s as buyer";
    public static final String MESSAGE_LINK_SELLER_SUCCESS =
            "Linked Property IDs: %1$s with Person IDs: %2$s as seller";

    private final LinkDescriptor linkDescriptor;

    /**
     * Creates a LinkCommand to link the specified {@code Properties} to the specified {@code People}
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

        List<Person> targetPeople = linkDescriptor.getPeopleInList(lastShownPersonList);
        List<Property> targetProperties = linkDescriptor.getPropertiesInList(lastShownPropertyList);

        List<Person> updatedPeople = linkDescriptor.getUpdatedPeople(lastShownPersonList);
        List<Property> updatedProperties = linkDescriptor.getUpdatedProperties(lastShownPropertyList);

        Stream.iterate(0, x -> x < targetPeople.size(), x -> x + 1)
                .forEach(i -> model.setPerson(targetPeople.get(i), updatedPeople.get(i)));
        Stream.iterate(0, x -> x < targetProperties.size(), x -> x + 1)
                .forEach(i -> model.setProperty(targetProperties.get(i), updatedProperties.get(i)));

        switch (linkDescriptor.getRelationship()) {
        case "buyer":
            return new CommandResult(String.format(MESSAGE_LINK_BUYER_SUCCESS, linkDescriptor.getPropertyIds(),
                    linkDescriptor.getPersonIds()));
        case "seller":
            return new CommandResult(String.format(MESSAGE_LINK_SELLER_SUCCESS, linkDescriptor.getPropertyIds(),
                    linkDescriptor.getPersonIds()));
        default:
            throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand otherLinkCommand = (LinkCommand) other;
        return linkDescriptor.equals(otherLinkCommand.linkDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("linkDescriptor", linkDescriptor)
                .toString();
    }

    /**
     * Stores Ids and relationship to link a property to a person.
     */
    public static class LinkDescriptor {
        private Set<Uuid> personIds;
        private Set<Uuid> propertyIds;
        private String relationship;

        public LinkDescriptor() {}

        /**
         * Copy constructor.
         */
        public LinkDescriptor(LinkDescriptor toCopy) {
            setPersonIds(toCopy.personIds);
            setPropertyIds(toCopy.propertyIds);
            setRelationship(toCopy.relationship);
        }

        public void setPersonIds(Set<Uuid> personIds) {
            this.personIds = personIds;
        }

        public Set<Uuid> getPersonIds() {
            return personIds;
        }

        public void setPropertyIds(Set<Uuid> propertyIds) {
            this.propertyIds = propertyIds;
        }

        public Set<Uuid> getPropertyIds() {
            return propertyIds;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getRelationship() {
            return relationship;
        }

        /**
         * Returns the {@code List<Person>} in the list with all matching personIds.
         *
         * @throws CommandException if any person is not found in the list.
         */
        public List<Person> getPeopleInList(List<Person> personList) throws CommandException {
            assert (personList != null);
            List<Person> peopleList = List.copyOf(personList).stream()
                    .filter(person -> personIds.contains(person.getUuid()))
                    .collect(Collectors.toList());
            if (peopleList.size() != personIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            return peopleList;
        }

        /**
         * Returns the {@code List<Property>} in the list with the matching propertyId.
         *
         * @throws CommandException if any property is not found in the list.
         */
        public List<Property> getPropertiesInList(List<Property> propertyList) throws CommandException {
            assert (propertyList != null);
            List<Property> propertiesList = List.copyOf(propertyList).stream()
                    .filter(property -> propertyIds.contains(property.getUuid()))
                    .collect(Collectors.toList());
            if (propertiesList.size() != propertyIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
            }
            return propertiesList;
        }

        /**
         * Returns an edited {@code List<Person>} with the properties linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public List<Person> getUpdatedPeople(List<Person> personList) throws CommandException {
            assert (relationship != null);
            List<Person> peopleToEdit = List.copyOf(getPeopleInList(personList));
            switch (relationship) {
            case "buyer":
                return peopleToEdit.stream()
                        .map(personToEdit -> personToEdit
                        .duplicateWithNewBuyingPropertyIds(
                        Stream.concat(personToEdit.getBuyingPropertyIds().stream(), propertyIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            case "seller":
                return peopleToEdit.stream()
                        .map(personToEdit -> personToEdit
                        .duplicateWithNewSellingPropertyIds(
                        Stream.concat(personToEdit.getSellingPropertyIds().stream(), propertyIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
            }
        }

        /**
         * Returns an edited {@code List<Property>} with the people linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public List<Property> getUpdatedProperties(List<Property> propertyList)
                throws CommandException {
            assert (relationship != null);
            List<Property> propertiesToEdit = List.copyOf(getPropertiesInList(propertyList));
            switch (relationship) {
            case "buyer":
                return propertiesToEdit.stream()
                        .map(propertyToEdit -> propertyToEdit
                        .duplicateWithNewBuyingPersonIds(
                        Stream.concat(propertyToEdit.getBuyingPersonIds().stream(), propertyIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            case "seller":
                return propertiesToEdit.stream()
                        .map(propertyToEdit -> propertyToEdit
                        .duplicateWithNewSellingPersonIds(
                        Stream.concat(propertyToEdit.getSellingPersonIds().stream(), propertyIds.stream())
                        .distinct()
                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
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

            return personIds.equals(otherLinkDescriptor.personIds)
                    && propertyIds.equals(otherLinkDescriptor.propertyIds)
                    && relationship.equals(otherLinkDescriptor.relationship);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("personIds", personIds)
                    .add("relationship", relationship)
                    .add("propertyIds", propertyIds)
                    .toString();
        }
    }
}
