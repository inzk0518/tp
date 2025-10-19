package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.model.uuid.Uuid;

class DeletePropertyCommandParserTest {

    private static final Uuid VALID_PROPERTY_ID = new Uuid(1, PROPERTY);

    private final DeletePropertyCommandParser parser = new DeletePropertyCommandParser();

    @Test
    void parse_validArgs_returnsDeletePropertyCommand() {
        assertParseSuccess(parser, "1", new DeletePropertyCommand(VALID_PROPERTY_ID));
        assertParseSuccess(parser, " 1 ", new DeletePropertyCommand(VALID_PROPERTY_ID));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "abc12", expectedMessage);
        assertParseFailure(parser, "abc12#", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
    }
}
