package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.ContactBuilderUtil;
import seedu.address.testutil.PropertyBuilderUtil;

class AddPropertyCommandTest {

    @Test
    void constructor_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPropertyCommand(null));
    }

    @Test
    void execute_propertyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPropertyAdded modelStub = new ModelStubAcceptingPropertyAdded();
        Property validProperty = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("1").build();

        CommandResult commandResult = new AddPropertyCommand(validProperty).execute(modelStub);
        assertEquals(String.format(AddPropertyCommand.MESSAGE_SUCCESS, Messages.format(validProperty)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validProperty), modelStub.propertiesAdded);
    }

    @Test
    void execute_ownerMissing_throwsCommandException() {
        ModelStubWithoutOwner modelStub = new ModelStubWithoutOwner();
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("999").build();
        AddPropertyCommand command = new AddPropertyCommand(property);

        assertThrows(CommandException.class,
                String.format(
                    AddPropertyCommand.MESSAGE_OWNER_NOT_FOUND, property.getOwner().value
                    ), () ->
                    command.execute(modelStub));
    }

    @Test
    void execute_ownerIdNotNumeric_throwsCommandException() {
        ModelStubAcceptingPropertyAdded modelStub = new ModelStubAcceptingPropertyAdded();
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("abc").build();
        AddPropertyCommand command = new AddPropertyCommand(property);

        assertThrows(CommandException.class,
                String.format(
                    AddPropertyCommand.MESSAGE_OWNER_NOT_FOUND, property.getOwner().value
                    ), () ->
                    command.execute(modelStub));
    }

    @Test
    void execute_duplicateProperty_throwsCommandException() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("1").build();
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);
        ModelStub modelStub = new ModelStubWithProperty(property);

        assertThrows(CommandException.class,
                AddPropertyCommand.MESSAGE_DUPLICATE_PROPERTY, () -> addPropertyCommand.execute(modelStub));
    }

    @Test
    void equals() {
        Property alpha = PROPERTY_ALPHA;
        Property beta = PROPERTY_BETA;
        AddPropertyCommand addAlphaCommand = new AddPropertyCommand(alpha);
        AddPropertyCommand addBetaCommand = new AddPropertyCommand(beta);

        assertTrue(addAlphaCommand.equals(addAlphaCommand));
        assertTrue(addAlphaCommand.equals(new AddPropertyCommand(PROPERTY_ALPHA)));
        assertFalse(addAlphaCommand.equals(1));
        assertFalse(addAlphaCommand.equals(addBetaCommand));
    }

    @Test
    void toStringMethod() {
        Property property = PROPERTY_ALPHA;
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);
        String expected = AddPropertyCommand.class.getCanonicalName() + "{toAdd=" + property + "}";
        assertEquals(expected, addPropertyCommand.toString());
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
        public void addContact(Contact contact) {
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
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
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
            return new PropertyBook(); // return empty property book
        }

        @Override
        public boolean hasProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProperty(Property target) {
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

        @Override
        public Property getPropertyById(Uuid id) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private static class ModelStubWithProperty extends ModelStub {
        private final Property property;
        private final AddressBook addressBook;

        ModelStubWithProperty(Property property) {
            requireNonNull(property);
            this.property = property;
            this.addressBook = new AddressBook();
            this.addressBook.addContact(new ContactBuilderUtil().withUuid(1).build());
        }

        @Override
        public boolean hasProperty(Property property) {
            requireNonNull(property);
            return this.property.isSameProperty(property);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }
    }

    private static class ModelStubAcceptingPropertyAdded extends ModelStub {
        final List<Property> propertiesAdded = new ArrayList<>();
        private final AddressBook addressBook;

        private ModelStubAcceptingPropertyAdded() {
            addressBook = new AddressBook();
            addressBook.addContact(new ContactBuilderUtil().withUuid(1).build());
        }

        @Override
        public boolean hasProperty(Property property) {
            requireNonNull(property);
            return propertiesAdded.stream().anyMatch(property::isSameProperty);
        }

        @Override
        public void addProperty(Property property) {
            requireNonNull(property);
            propertiesAdded.add(property);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }
    }

    private static class ModelStubWithoutOwner extends ModelStub {
        private final AddressBook addressBook = new AddressBook();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public boolean hasProperty(Property property) {
            return false;
        }

        @Override
        public void addProperty(Property property) {
            // no-op for test
        }
    }
}
