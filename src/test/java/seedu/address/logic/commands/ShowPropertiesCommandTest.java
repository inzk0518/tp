package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.uuid.Uuid;

public class ShowPropertiesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

    @Test
    public void execute_validUuid_success() throws Exception {
        Uuid validUuid = new Uuid(1, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(validUuid);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Listed")
                || result.getFeedbackToUser().contains("No properties found"));
    }

    @Test
    public void execute_noPropertiesFound_showsMessage() throws Exception {
        Uuid uuidWithNoProperties = new Uuid(999, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuidWithNoProperties);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("No properties found"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Uuid uuid = new Uuid(1, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        assertTrue(command.equals(command));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Uuid uuid = new Uuid(1, PERSON);
        ShowPropertiesCommand command1 = new ShowPropertiesCommand(uuid);
        ShowPropertiesCommand command2 = new ShowPropertiesCommand(uuid);

        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Uuid uuid1 = new Uuid(1, PERSON);
        Uuid uuid2 = new Uuid(2, PERSON);

        ShowPropertiesCommand command1 = new ShowPropertiesCommand(uuid1);
        ShowPropertiesCommand command2 = new ShowPropertiesCommand(uuid2);

        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Uuid uuid = new Uuid(1, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        assertFalse(command.equals("not a command"));
    }

    @Test
    public void equals_null_returnsFalse() {
        Uuid uuid = new Uuid(1, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        assertFalse(command.equals(null));
    }

    @Test
    public void toString_containsUuid() {
        Uuid uuid = new Uuid(1, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);

        String result = command.toString();
        assertTrue(result.contains("clientUuid"));
    }
}
