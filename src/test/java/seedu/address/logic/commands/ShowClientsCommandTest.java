package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.uuid.Uuid;

public class ShowClientsCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

    @Test
    public void execute_validProperty_success() throws Exception {
        Uuid validUuid = new Uuid(1, PROPERTY);
        ShowClientsCommand command = new ShowClientsCommand(validUuid);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("Listed")
                || result.getFeedbackToUser().contains("No clients found"));
    }

    @Test
    public void execute_noClientsFound_showsMessage() throws Exception {
        Uuid uuidWithNoClients = new Uuid(999, PROPERTY);
        ShowClientsCommand command = new ShowClientsCommand(uuidWithNoClients);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("No clients found")
                || result.getFeedbackToUser().contains("No clients"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Uuid uuid = new Uuid(1, PROPERTY);
        ShowClientsCommand command = new ShowClientsCommand(uuid);
        assertEquals(command, command);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Uuid uuid = new Uuid(1, PROPERTY);
        ShowClientsCommand command1 = new ShowClientsCommand(uuid);
        ShowClientsCommand command2 = new ShowClientsCommand(uuid);
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ShowClientsCommand c1 = new ShowClientsCommand(new Uuid(1, PROPERTY));
        ShowClientsCommand c2 = new ShowClientsCommand(new Uuid(2, PROPERTY));
        assertNotEquals(c1, c2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ShowClientsCommand command = new ShowClientsCommand(new Uuid(1, PROPERTY));
        assertNotEquals("string", command);
    }

    @Test
    public void equals_null_returnsFalse() {
        ShowClientsCommand command = new ShowClientsCommand(new Uuid(1, PROPERTY));
        assertNotEquals(null, command);
    }

    @Test
    public void toString_containsUuid() {
        Uuid uuid = new Uuid(1, PROPERTY);
        ShowClientsCommand command = new ShowClientsCommand(uuid);
        assertTrue(command.toString().contains("propertyUuid"));
    }
}
