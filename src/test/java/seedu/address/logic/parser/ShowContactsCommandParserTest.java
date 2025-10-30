package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowContactsCommand;
import seedu.address.model.uuid.Uuid;

public class ShowContactsCommandParserTest {

    private final ShowContactsCommandParser parser = new ShowContactsCommandParser();

    @Test
    public void parse_validArgs_returnsShowContactsCommand() {
        Uuid expectedUuid = new Uuid(1, PROPERTY);
        assertParseSuccess(parser, " " + PREFIX_PROPERTY_ID + "1", new ShowContactsCommand(expectedUuid));
    }

    @Test
    public void parse_largeValidArgs_returnsShowContactsCommand() {
        Uuid expectedUuid = new Uuid(999, PROPERTY);
        assertParseSuccess(parser, " " + PREFIX_PROPERTY_ID + "999", new ShowContactsCommand(expectedUuid));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidUuidNonNumeric_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PROPERTY_ID + "abc", ParserUtil.MESSAGE_INVALID_UUID);
    }

    @Test
    public void parse_invalidUuidNegative_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PROPERTY_ID + "-1", ParserUtil.MESSAGE_INVALID_UUID);
    }

    @Test
    public void parse_invalidUuidZero_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PROPERTY_ID + "0", ParserUtil.MESSAGE_INVALID_UUID);
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(parser, "extra text " + PREFIX_PROPERTY_ID + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsShowContactsCommand() {
        Uuid expectedUuid = new Uuid(5, PROPERTY);
        assertParseSuccess(parser, "   " + PREFIX_PROPERTY_ID + "5   ", new ShowContactsCommand(expectedUuid));
    }

    @Test
    public void parse_invalidPrefixCase_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PROPERTY_ID.toString().toUpperCase() + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
    }
}
