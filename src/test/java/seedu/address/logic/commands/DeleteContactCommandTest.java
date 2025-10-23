package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteContactCommand.MESSAGE_CONTACT_NOT_FOUND;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.uuid.Uuid;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteContactCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new PropertyBook(), new UserPrefs());

    @Test
    public void execute_validUuidUnfilteredList_success() {
        Contact contactToDelete = model.getFilteredContactList().get(0);
        Uuid uuidToDelete = contactToDelete.getUuid();
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(uuidToDelete);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(deleteContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUuidUnfilteredList_throwsCommandException() {
        Uuid invalidUuid = new Uuid(999999, Uuid.StoredItem.CONTACT); // Non-existent UUID
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(invalidUuid);

        assertCommandFailure(deleteContactCommand, model,
                String.format(MESSAGE_CONTACT_NOT_FOUND, invalidUuid.getValue()));
    }

    @Test
    public void equals() {
        Uuid firstUuid = model.getFilteredContactList().get(0).getUuid();
        Uuid secondUuid = model.getFilteredContactList().get(1).getUuid();

        DeleteContactCommand deleteFirstCommand = new DeleteContactCommand(firstUuid);
        DeleteContactCommand deleteSecondCommand = new DeleteContactCommand(secondUuid);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteContactCommand deleteFirstCommandCopy = new DeleteContactCommand(firstUuid);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different contact -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Uuid targetUuid = model.getFilteredContactList().get(0).getUuid();
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(targetUuid);
        String expected = DeleteContactCommand.class.getCanonicalName() + "{targetUuid=" + targetUuid + "}";
        assertEquals(expected, deleteContactCommand.toString());
    }
}
