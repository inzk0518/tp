package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

class PropertyBookParserTest {

    private static final String VALID_ADD_PROPERTY_COMMAND = String.join(" ",
            AddPropertyCommand.COMMAND_WORD,
            "a/123 Main St 5",
            "postal/123456",
            "price/500000",
            "type/HDB",
            "status/unavailable",
            "bed/3",
            "bath/2",
            "f/120",
            "o/owner123",
            "l/sale");

    private static final String VALID_DELETE_PROPERTY_COMMAND = String.join(" ",
            DeletePropertyCommand.COMMAND_WORD,
            "123");

    private final PropertyBookParser parser = new PropertyBookParser();

    @Test
    void parseCommand_addProperty() throws Exception {
        Property expectedProperty = PROPERTY_ALPHA;
        AddPropertyCommand expectedCommand = new AddPropertyCommand(expectedProperty);

        AddPropertyCommand command = (AddPropertyCommand) parser.parseCommand(VALID_ADD_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_deleteProperty() throws Exception {
        DeletePropertyCommand expectedCommand = new DeletePropertyCommand(new Uuid(123, PROPERTY));
        DeletePropertyCommand command = (DeletePropertyCommand) parser.parseCommand(VALID_DELETE_PROPERTY_COMMAND);
        assertTrue(command.equals(expectedCommand));
    }

    @Test
    void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("random"));
    }

    @Test
    void parseCommand_blankInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                    parser.parseCommand("   "));
    }

    @Test
    void parseCommand_invalidArguments_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddPropertyCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(DeletePropertyCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_filterProperty() throws Exception {
        assertTrue(parser.parseCommand("filterproperty type/hdb") instanceof FilterPropertyCommand);
    }
}
