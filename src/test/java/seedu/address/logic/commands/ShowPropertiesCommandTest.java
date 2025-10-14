package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Uuid;

public class ShowPropertiesCommandTest {

    @Test
    public void equals() {
        Uuid firstUuid = new Uuid(123);
        Uuid secondUuid = new Uuid(456);

        ShowPropertiesCommand showFirstCommand = new ShowPropertiesCommand(firstUuid);
        ShowPropertiesCommand showSecondCommand = new ShowPropertiesCommand(secondUuid);

        // same object will returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values will return true
        ShowPropertiesCommand showFirstCommandCopy = new ShowPropertiesCommand(firstUuid);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types will return false
        assertFalse(showFirstCommand.equals(1));

        // null value will returns false
        assertFalse(showFirstCommand.equals(null));

        // different uuid will return false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Uuid uuid = new Uuid(123);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);
        String expected = ShowPropertiesCommand.class.getCanonicalName()
                + "{clientUuid=" + uuid + "}";
        assertEquals(expected, command.toString());
    }
}
