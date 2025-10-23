package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowClientsCommand;
import seedu.address.model.uuid.Uuid;

public class ShowClientsCommandParserTest {

    private final ShowClientsCommandParser parser = new ShowClientsCommandParser();

    @Test
    public void parse_validArgs_returnsShowClientsCommand() {
        Uuid expectedUuid = new Uuid(1, PROPERTY);
        assertParseSuccess(parser, " p/1", new ShowClientsCommand(expectedUuid));
    }

    @Test
    public void parse_largeValidArgs_returnsShowClientsCommand() {
        Uuid expectedUuid = new Uuid(999, PROPERTY);
        assertParseSuccess(parser, " p/999", new ShowClientsCommand(expectedUuid));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidUuidNonNumeric_throwsParseException() {
        assertParseFailure(parser, " p/abc", ParserUtil.MESSAGE_INVALID_UUID);
    }

    @Test
    public void parse_invalidUuidNegative_throwsParseException() {
        assertParseFailure(parser, " p/-1", ParserUtil.MESSAGE_INVALID_UUID);
    }

    @Test
    public void parse_invalidUuidZero_throwsParseException() {
        assertParseFailure(parser, " p/0", ParserUtil.MESSAGE_INVALID_UUID);
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(parser, "extra text p/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsShowClientsCommand() {
        Uuid expectedUuid = new Uuid(5, PROPERTY);
        assertParseSuccess(parser, "   p/5   ", new ShowClientsCommand(expectedUuid));
    }

    @Test
    public void parse_invalidPrefixCase_throwsParseException() {
        assertParseFailure(parser, " P/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE));
    }
}
