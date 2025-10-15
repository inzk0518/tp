package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Unlinks properties from people.
 */
public class UnlinkCommand extends Command {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": unlinks properties from people. "
            + "Parameters: "
            + PREFIX_LINK_PROPERTY_ID + "PROPERTY_ID{1..} "
            + PREFIX_LINK_CLIENT_ID + "CLIENT_ID{1..}"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LINK_PROPERTY_ID + "2 "
            + PREFIX_LINK_PROPERTY_ID + "5 "
            + PREFIX_LINK_CLIENT_ID + "3";

    public static final String MESSAGE_UNLINK_SUCCESS =
            "Uninked Property IDs: %1$s with Person IDs: %2$s";

    private final UnlinkDescriptor unlinkDescriptor;

    /**
     * Creates an UnlinkCommand to unlink the specified {@code Properties} to the specified {@code People}
     */
    public UnlinkCommand(UnlinkDescriptor unlinkDescriptor) {
        requireNonNull(unlinkDescriptor);
        this.unlinkDescriptor = unlinkDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Property> lastShownPropertyList = model.getFilteredPropertyList();

        List<Person> targetPeople = unlinkDescriptor.getPeopleInList(lastShownPersonList);
        List<Property> targetProperties = unlinkDescriptor.getPropertiesInList(lastShownPropertyList);

        List<Person> updatedPeople = unlinkDescriptor.getUpdatedPeople(lastShownPersonList);
        List<Property> updatedProperties = unlinkDescriptor.getUpdatedProperties(lastShownPropertyList);

        Stream.iterate(0, x -> x < targetPeople.size(), x -> x + 1)
                .forEach(i -> model.setPerson(targetPeople.get(i), updatedPeople.get(i)));
        Stream.iterate(0, x -> x < targetProperties.size(), x -> x + 1)
                .forEach(i -> model.setProperty(targetProperties.get(i), updatedProperties.get(i)));

        return new CommandResult(String.format(MESSAGE_UNLINK_SUCCESS, unlinkDescriptor.getPropertyIds(),
                    unlinkDescriptor.getPersonIds()));
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
     * Stores Ids to unlink a property to a person.
     */
    public static class UnlinkDescriptor {
        private Set<Uuid> personIds;
        private Set<Uuid> propertyIds;

        public UnlinkDescriptor() {}

        /**
         * Copy constructor.
         */
        public UnlinkDescriptor(UnlinkDescriptor toCopy) {
            setPersonIds(toCopy.personIds);
            setPropertyIds(toCopy.propertyIds);
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

        /**
         * Returns the {@code List<Person>} in the list with all matching personIds.
         *
         * @throws CommandException if any person is not found in the list.
         */
        public List<Person> getPeopleInList(List<Person> personList) throws CommandException {
            assert (personList != null);
            List<Person> peopleList = new ArrayList<>(personList).stream()
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
            List<Property> propertiesList = new ArrayList<>(propertyList).stream()
                    .filter(property -> propertyIds.contains(property.getUuid()))
                    .collect(Collectors.toList());
            if (propertiesList.size() != propertyIds.size()) {
                throw new CommandException(MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
            }
            return propertiesList;
        }

        /**
         * Returns an edited {@code List<Person>} with the properties unlinked.
         */
        public List<Person> getUpdatedPeople(List<Person> personList) throws CommandException {
            List<Person> peopleToEdit = getPeopleInList(personList);
            return new ArrayList<>(peopleToEdit).stream()
                    .map(personToEdit -> personToEdit
                    .duplicateWithNewBuyingPropertyIds(
                    personToEdit.getBuyingPropertyIds().stream().filter(id -> !propertyIds.contains(id))
                    .collect(Collectors.toSet()))
                    .duplicateWithNewSellingPropertyIds(
                    personToEdit.getSellingPropertyIds().stream().filter(id -> !propertyIds.contains(id))
                    .collect(Collectors.toSet())))
                    .collect(Collectors.toList());
        }

        /**
         * Returns an edited {@code List<Property>} with the people unlinked.
         */
        public List<Property> getUpdatedProperties(List<Property> propertyList) throws CommandException {
            List<Property> propertiesToEdit = getPropertiesInList(propertyList);
            return new ArrayList<>(propertiesToEdit).stream()
                    .map(propertyToEdit -> propertyToEdit
                    .duplicateWithNewBuyingPersonIds(
                    propertyToEdit.getBuyingPersonIds().stream().filter(id -> !personIds.contains(id))
                    .collect(Collectors.toSet()))
                    // Set.of())
                    .duplicateWithNewSellingPersonIds(
                    propertyToEdit.getSellingPersonIds().stream().filter(id -> !personIds.contains(id))
                    .collect(Collectors.toSet())))
                    // Set.of()))
                    .collect(Collectors.toList());
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

            return personIds.equals(otherUnlinkDescriptor.personIds)
                    && propertyIds.equals(otherUnlinkDescriptor.propertyIds);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("personIds", personIds)
                    .add("propertyIds", propertyIds)
                    .toString();
        }
    }
}
