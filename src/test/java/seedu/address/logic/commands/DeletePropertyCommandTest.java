package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;

class DeletePropertyCommandTest {

    @Test
    void constructor_nullPropertyId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePropertyCommand(null));
    }

    @Test
    void execute_propertyExists_successfulDeletion() throws Exception {
        Property property = buildProperty("123 Main St");
        ModelStubWithProperties modelStub = new ModelStubWithProperties(property);
        DeletePropertyCommand command = new DeletePropertyCommand(property.getId());

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(DeletePropertyCommand.MESSAGE_DELETE_PROPERTY_SUCCESS, property.getId()),
                result.getFeedbackToUser());
        assertEquals(List.of(property), modelStub.deletedProperties);
        assertTrue(modelStub.properties.isEmpty());
    }

    @Test
    void execute_propertyMissing_throwsCommandException() {
        Property property = buildProperty("123 Main St");
        ModelStubWithProperties modelStub = new ModelStubWithProperties(property);
        String absentId = deriveDifferentId(property.getId());
        DeletePropertyCommand command = new DeletePropertyCommand(absentId);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX,
                () -> command.execute(modelStub));
        assertEquals(List.of(property), modelStub.properties);
    }

    @Test
    void equals() {
        DeletePropertyCommand deleteAlpha = new DeletePropertyCommand("abc123");
        DeletePropertyCommand deleteAlphaUpper = new DeletePropertyCommand("ABC123");
        DeletePropertyCommand deleteBeta = new DeletePropertyCommand("def456");

        assertTrue(deleteAlpha.equals(deleteAlpha));
        assertTrue(deleteAlpha.equals(deleteAlphaUpper));
        assertFalse(deleteAlpha.equals(1));
        assertFalse(deleteAlpha.equals(deleteBeta));
    }

    @Test
    void toStringMethod() {
        DeletePropertyCommand command = new DeletePropertyCommand("abc123");
        String expected = DeletePropertyCommand.class.getCanonicalName() + "{targetPropertyId=abc123}";
        assertEquals(expected, command.toString());
    }

    private static Property buildProperty(String address) {
        return new Property(new PropertyAddress(address), new Bathroom("2"), new Bedroom("3"),
                new FloorArea("120"), new Listing("sale"), new Postal("123456"), new Price("500000"),
                new Status("listed"), new Type("HDB"), new Owner("owner123"));
    }

    private static String deriveDifferentId(String propertyId) {
        requireNonNull(propertyId);
        char replacement = propertyId.charAt(0) == 'a' ? 'b' : 'a';
        return replacement + propertyId.substring(1);
    }

    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPropertyBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPropertyBookFilePath(Path propertyBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPropertyBook(ReadOnlyPropertyBook propertyBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPropertyBook getPropertyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProperty(Property target, Property editedProperty) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPropertyList(Predicate<Property> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private static class ModelStubWithProperties extends ModelStub {
        final ObservableList<Property> properties = FXCollections.observableArrayList();
        final List<Property> deletedProperties = new ArrayList<>();

        ModelStubWithProperties(Property... properties) {
            this.properties.addAll(Arrays.asList(properties));
        }

        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            return properties;
        }

        @Override
        public void deleteProperty(Property target) {
            requireNonNull(target);
            if (!properties.remove(target)) {
                throw new AssertionError("The target property should be present.");
            }
            deletedProperties.add(target);
        }
    }
}
