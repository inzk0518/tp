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

    public static final String MESSAGE_LINK_BUYER_SUCCESS =
            "Linked Property IDs: %1$s with Person IDs: %2$s as buyer";
    public static final String MESSAGE_LINK_SELLER_SUCCESS =
            "Linked Property IDs: %1$s with Person IDs: %2$s as seller";

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

    /**
     * Stores Ids and relationship to link a property to a person.
     */
    public static class LinkDescriptor {
        private Set<Uuid> personIds;
        private Set<String> propertyIds;
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

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(personIds, propertyIds, relationship);
        }

        public void setPersonIds(Set<Uuid> personIds) {
            this.personIds = personIds;
        }

        public Set<Uuid> getPersonIds() {
            return personIds;
        }

        public void setPropertyIds(Set<String> propertyIds) {
            this.propertyIds = propertyIds;
        }

        public Set<String> getPropertyIds() {
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
            List<Person> peopleList = personList.stream()
                    .filter(person -> personIds.contains(person.getUuid()))
                    .collect(Collectors.toList());
            if (peopleList.size() != personIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            return peopleList;
        }

        /**
         * Returns the {@code Set<Property>} in the list with the matching propertyId.
         *
         * @throws CommandException if no such property exists in the list.
         */
        public List<Property> getPropertiesInList(List<Property> propertyList) throws CommandException {
            List<Property> propertiesList = propertyList.stream()
                    .filter(property -> propertyIds.contains(property.getId()))
                    .collect(Collectors.toList());
            if (propertiesList.size() != propertyIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
            }
            return propertiesList;
        }

        /**
         * Returns an edited {@code Set<Person>} with the properties linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public List<Person> getUpdatedPeople(List<Person> personList) throws CommandException {
            List<Person> peopleToEdit = getPeopleInList(personList);
            switch (relationship) {
            case "buyer":
                return peopleToEdit.stream().map(personToEdit -> new Person(personToEdit.getUuid(),
                        personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                        personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getBudgetMin(),
                        personToEdit.getBudgetMax(), personToEdit.getNotes(), personToEdit.getStatus(),
                        Stream.concat(personToEdit.getBuyingPropertyIds().stream(), propertyIds.stream())
                                .collect(Collectors.toSet()),
                        personToEdit.getSellingPropertyIds()))
                        .collect(Collectors.toList());
            case "seller":
                return peopleToEdit.stream().map(personToEdit -> new Person(personToEdit.getUuid(),
                        personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                        personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getBudgetMin(),
                        personToEdit.getBudgetMax(), personToEdit.getNotes(), personToEdit.getStatus(),
                        personToEdit.getBuyingPropertyIds(),
                        Stream.concat(personToEdit.getSellingPropertyIds().stream(), propertyIds.stream())
                                .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_RELATIONSHIP);
            }
        }

        /**
         * Returns an edited {@code Set<Property>} with the people linked.
         *
         * @throws CommandException if the relationship is invalid.
         */
        public List<Property> getUpdatedProperties(List<Property> propertyList)
                throws CommandException {
            List<Property> propertiesToEdit = getPropertiesInList(propertyList);
            switch (relationship) {
            case "buyer":
                propertiesToEdit.stream().forEach(property -> property.setBuyingPersonIds(
                        Stream.concat(property.getBuyingPersonIds().stream(), personIds.stream())
                                .collect(Collectors.toSet())));
                return propertiesToEdit;
            case "seller":
                propertiesToEdit.stream().forEach(property -> property.setSellingPersonIds(
                        Stream.concat(property.getSellingPersonIds().stream(), personIds.stream())
                                .collect(Collectors.toSet())));
                return propertiesToEdit;
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
}
